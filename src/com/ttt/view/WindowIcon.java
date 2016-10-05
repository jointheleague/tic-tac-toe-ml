package com.ttt.view;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.apple.eawt.Application;

public class WindowIcon {
	
	public static void setupIcons(JFrame frame){
		//PC App Icons
			ImageIcon img = new ImageIcon("res/Icon.png");
			frame.setIconImage(img.getImage());
		//Mac App Icons
			Application application = Application.getApplication();
			Image image = Toolkit.getDefaultToolkit().getImage("res/Icon.png");
			application.setDockIconImage(image);
			
		//TODO: Figure out Linux
			
		//TODO: Figure out Solaris (Lol, jk)
				
	}

}
