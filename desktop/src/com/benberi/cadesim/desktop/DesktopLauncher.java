package com.benberi.cadesim.desktop;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.benberi.cadesim.BlockadeSimulator;
import com.benberi.cadesim.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		int[] res = new int[2];
		BlockadeSimulator cadesim = new BlockadeSimulator();
		res = loadresolution();
		config.resizable = false;
		config.width = res[0];
		config.height = res[1];
		//config.backgroundFPS = 20;    // bugfix high CPU
		config.vSyncEnabled = false; // "
		config.title = Constants.name + " (version " + Constants.VERSION + ")";
		new LwjglApplication(cadesim, config);
	}

	private static int[] loadresolution() {
		Properties prop = new Properties();
		String fileName = "user.config";
		InputStream is = null;
		try {
		    is = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		}
	
		try {
		    prop.load(is);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    int[] resolution = new int[2];
		resolution[0] = Integer.parseInt(prop.getProperty("user.width"));
		resolution[1] = Integer.parseInt(prop.getProperty("user.height"));
		return resolution;
	}
}