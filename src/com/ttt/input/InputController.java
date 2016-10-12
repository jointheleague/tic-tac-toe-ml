package com.ttt.input;

import javax.awt.ClickListener;
import javax.awt.Shape;

import com.ttt.view.RenderService;

public class InputController {
	public static void registerClickInput(RenderService renderService) {
		renderService.setClickListener(new ClickListener() {
			@Override
			public void onClick(int mouseX, int mouseY) {
			}

			@Override
			public void onButtonClick(Shape source, int mouseX, int mouseY) {
				// TicTacToe.getBoard().setTile(source.getX() /
				// RenderService.tileWidth, source.getY()
				// / RenderService.tileHeight],
				// TicTacToe.getBoard().getCurrentTurn());
			}
		});
	}
}
