package com.ttt.view;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class WindowIcon {
	public static void setupIcons(JFrame frame) {
		String os = System.getProperty("os.name").toLowerCase();

		if (os.contains("windows")) {
			ImageIcon img = new ImageIcon("res/Icon.png");
			frame.setIconImage(img.getImage());
		} else if (os.contains("mac")) {
			try {
				Class<?> appClass = Class.forName("com.apple.eawt.Application");
				Object appObj = appClass.getMethod("getApplication").invoke(null);
				Image image = Toolkit.getDefaultToolkit().getImage("res/Icon.png");
				appClass.getMethod("setDockIconImage", Image.class).invoke(appObj, image);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("We all like Linux, but not for playing Tic-Tac-Toe.");
		}
	}

}
