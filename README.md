Info
====

Fork of the [SikuliRemoteWebdriver](https://github.com/AJ-72/SikuliRemoteWebdriver)
See also 

 *  [sikuli-webdriver](https://github.com/edno/sikuli-webdriver)
 *  [sikuli-api](https://github.com/sikuli/sikuli-api)
 *  [Sikuli-on-Selenium](https://github.com/mubbashir/Sikuli-on-Selenium) - Sikuli metadata TestNG integration 
 *  [ashot](https://github.com/yandex-qatools/ashot) - yandex WebDriver Screenshot utility 


TODO
-----
* Vagrantfile to deploy Sikuli and Selenium node jars together. Available examples:
 - [sikuli-and-vagrant](https://prezi.com/x1jto6svmecr/sikuli-and-vagrant/)
 - [building dependencies](https://bugs.launchpad.net/sikuli/+bug/1349255/+attachment/4165859/+files/sikuli_install.sh)
 - [robot-sikuli-java](https://github.com/hasanen/robot-sikuli-java)
 perform simple jar deployment but  no intergation.
* Switch  to [sikiliX](https://github.com/RaiMan/SikuliX-2014). Note there is no 1:1 correspondence between the org.sikuli.api.* in the sikuli project and org.sikuli.script.* in sikulix.

Semi-automated
--------------
To clone existing siluki insallation to a vanilla VM,
* creare or copy the java preferences registry directory:

```
home/vagrant/.java/
home/vagrant/.java/.userPrefs/
home/vagrant/.java/.userPrefs/.user.lock.vagrant
home/vagrant/.java/.userPrefs/.userRootModFile.vagrant
home/vagrant/.java/.userPrefs/org/
home/vagrant/.java/.userPrefs/org/prefs.xml
home/vagrant/.java/.userPrefs/org/sikuli/
home/vagrant/.java/.userPrefs/org/sikuli/prefs.xml
home/vagrant/.java/.userPrefs/org/sikuli/basics/
home/vagrant/.java/.userPrefs/org/sikuli/basics/prefs.xml
home/vagrant/.java/fonts/
home/vagrant/.java/fonts/1.7.0_95/
home/vagrant/.java/fonts/1.7.0_95/fcinfo-1-vagrant-ubuntu-trusty-64-Ubuntu-14.04
-en.properties

```
* create the  sikuli directory:
```
./Desktop/sikuli/
./Desktop/sikuli/runIDE
./Desktop/sikuli/sikuli-setup.jar
./Desktop/sikuli/sikuli-ide.jar
./Desktop/sikuli/libs/
./Desktop/sikuli/libs/libJXGrabKey.so
./Desktop/sikuli/libs/MadeForSikuliX64L.txt
./Desktop/sikuli/libs/libVisionProxy.so
./Desktop/sikuli/SikuliX-1.0.1-SetupLog.txt
```
(wget locations of the above is WIP).
See Also
--------
[intro](http://devengineering.com/blog/testing/how-integrate-sikuli-script-selenium-webdriver)
