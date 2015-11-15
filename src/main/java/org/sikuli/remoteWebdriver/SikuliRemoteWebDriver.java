package org.sikuli.remoteWebdriver;


import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.sikuli.api.DefaultScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.Relative;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.visual.element.ImageElement;
public class SikuliRemoteWebDriver extends RemoteWebDriver
{

private static final int DEFAULT_WAIT_TIMEOUT_MSECS = 5000;
ScreenRegion webdriverRegion;
WebDriverScreen webDriverScreen;

public SikuliRemoteWebDriver(DesiredCapabilities dc){
	super(dc);
	try {
		webDriverScreen = new WebDriverScreen(this);
	} catch (IOException e1) {
		e1.printStackTrace();
		throw new RuntimeException("unable to initialize sikuli remote driver");

	}
	webdriverRegion = new DefaultScreenRegion(webDriverScreen);
}

public SikuliRemoteWebDriver(URL hubURL, DesiredCapabilities browserCap){
	super(hubURL,browserCap);

	try {
		webDriverScreen = new WebDriverScreen(this);
	} catch (IOException e1) {
		e1.printStackTrace();
		throw new RuntimeException("unable to initialize sikuli remote driver");
	}
	webdriverRegion = new DefaultScreenRegion(webDriverScreen);
}

public WebElement findElementByLocation(int x, int y){
	return (WebElement) ((JavascriptExecutor) this).executeScript("return document.elementFromPoint(" + x + "," + y + ")");
}

private long getDocumentHeight(){
	return (Long) ((JavascriptExecutor) this).executeScript("return document.body.clientHeight;");
}

private void scrollPage(int y){
	((JavascriptExecutor) this).executeScript("window.scrollBy(0,"+y+");");
}

public DefaultImageElement findImageElement(URL imageUrl) {
	ImageTarget target = new ImageTarget(imageUrl);
	ScreenRegion imageRegion=findImage(target);
	if(imageRegion!=null) {
		ScreenLocation center = imageRegion.getCenter();
		WebElement foundWebElement = findElementByLocation(center.getX(), center.getY());
		Rectangle r = imageRegion.getBounds();
		return (new DefaultImageElement(this, foundWebElement,r.x, r.y, r.width, r.height));
	}
	else{
		throw new ImageNotFoundException("Element matching the image was not found in the current page");
	}
}


private ScreenRegion findImage(ImageTarget target){

	//Get the entire height of the document to scroll through.
	int heightOfDoc =(int)getDocumentHeight();

	//Get the current height of the browser. This is the height we need
	int heightOfBrowser=webDriverScreen.getSize().height;

	//To know the value of height that we are currently at
	int currentHeight=heightOfBrowser;
	int y=0;

	boolean foundImage=false;
	ScreenRegion imageRegion=null;

	while((currentHeight<heightOfDoc)&&(!foundImage)) {
		//Sikuli method to find the target image in the current region
		imageRegion = webdriverRegion.wait(target, DEFAULT_WAIT_TIMEOUT_MSECS);

		if (imageRegion != null) {
			foundImage=true;
		}
		else{
			scrollPage(heightOfBrowser);
			if(!this.getCapabilities().getBrowserName().contains("chrome")) {
				currentHeight+=heightOfBrowser;
				y+=heightOfBrowser;
				webDriverScreen.setNewHeightForCropping(currentHeight);
				webDriverScreen.setY(y);
			}
		}
	}
	return imageRegion;
}

public ScreenRegion waitForImage(URL imageUrl,int timeout){
	ImageTarget target = new ImageTarget(imageUrl);
	webdriverRegion.wait(target, timeout);
	return webdriverRegion.find(target);
}

public DefaultImageElement findImageElementRelativeTo(URL imgToFind, URL relImg, int offsetToMove){
	ImageTarget target = new ImageTarget(relImg);
	ScreenRegion relativeImageRegion;
	ScreenRegion imageRegion=webdriverRegion;
	int heightOfDoc =(int)getDocumentHeight();
	int heightOfBrowser=webDriverScreen.getSize().height-100;
	int currentHeight=heightOfBrowser;
	int y=0;
	boolean foundImage=false;
	while((currentHeight<heightOfDoc)&&(!foundImage)) {
		relativeImageRegion= webdriverRegion.wait(target,DEFAULT_WAIT_TIMEOUT_MSECS);
		if(relativeImageRegion!=null)
			imageRegion =Relative.to(relativeImageRegion).below(offsetToMove).getScreenRegion();
		else
			imageRegion=null;

		if (imageRegion != null) {
			foundImage=true;
		}else{
			scrollPage(heightOfBrowser);
			if(!this.getCapabilities().getBrowserName().contains("chrome")) {
				currentHeight+=heightOfBrowser;
				y+=heightOfBrowser;
				webDriverScreen.setNewHeightForCropping(currentHeight);
				webDriverScreen.setY(y);
			}
		}

	}
	if(foundImage) {
		ScreenLocation center = imageRegion.getCenter();
		WebElement foundWebElement = findElementByLocation(center.getX(), center.getY());
		Rectangle r = imageRegion.getBounds();
		return new DefaultImageElement(this, foundWebElement, r.x,r.y,r.width,r.height);
	}

	else{
		return null;
	}
}


public boolean isImagePresentInPage(URL imageToCheck){
	ImageTarget target = new ImageTarget(imageToCheck);
	ScreenRegion outputImage = findImage(target);
	if(outputImage==null)
		return false;
	else
		return true;
}

}
