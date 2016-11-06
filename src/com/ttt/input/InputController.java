package com.ttt.input;

import javax.awt.ClickListener;

import javax.awt.Shape;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;
import com.ttt.model.TicTacToe;
import com.ttt.view.RenderService;

public class InputController {
	
	public static volatile Object clicked = new Object();
	public static volatile int tileX;
	public static volatile int tileY;
	
	public static void registerClickInput(RenderService renderService) {
		renderService.setClickListener(new ClickListener() {
			@Override
			public void onClick(int mouseX, int mouseY) {
			}

			@Override
			public void onButtonClick(Shape source, int mouseX, int mouseY) {
				
				
				synchronized (clicked) {
					clicked.notifyAll();
				}
				tileX = source.getX() / RenderService.tileWidth;
				tileY = source.getY() / RenderService.tileHeight;
				
			}
		});
	}
}
