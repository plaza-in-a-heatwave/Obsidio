# Obsidio
A blockade simulator for a game named Puzzle Pirates (Client-side)
The game server for this blockade simulator client repository can be found there: https://github.com/BenBeri/Obsidio-Server

# Installing

## Prerequisites
* Get eclipse, configure for java, gradle, mavern etc
* Oracle JDK - e.g. 12.0.1

## Eclipse config
* Load project into eclipse, refresh gradle, clean all
* Set JRE System Libraries to JavaSE-1.8 if not already
* In the desktop project, use DesktopLauncher.java as the main class
* Set workspace as Obsidio/
* add BlockadeSimulator-core to BlockadeSimulator-desktop 's project build path
* set BlockadeSimulator-core entire folder as a source

## Exporting
* Export as jar, make sure there is user.config in the same directory
* java -jar jarjar.jar