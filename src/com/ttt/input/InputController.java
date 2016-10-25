package com.ttt.input;

import javax.awt.ClickListener;

import javax.awt.Shape;

import com.ttt.model.TicTacToe;
import com.ttt.view.RenderService;

public class InputController {
	
	public static volatile boolean clicked = false;
	public static volatile int tileX;
	public static volatile int tileY;
	
	public static void registerClickInput(RenderService renderService) {
		renderService.setClickListener(new ClickListener() {
			@Override
			public void onClick(int mouseX, int mouseY) {
			}

			@Override
			public void onButtonClick(Shape source, int mouseX, int mouseY) {
				
				clicked = true;
				tileX = source.getX() / RenderService.tileWidth;
				tileY = source.getY() / RenderService.tileHeight;
				
			}
		});
	}
}
