package com.ttt.view;

import java.awt.Color;

import javax.awt.Ellipse;
import javax.awt.Fonts;
import javax.awt.GUIApplication;
import javax.awt.GraphicsStyle;
import javax.awt.Rectangle;
import javax.awt.Shape;
import javax.awt.Style;

import com.ttt.input.InputController;
import com.ttt.model.TicTacToe;
import com.ttt.model.Tile;

public class RenderService extends GUIApplication {
	private static final long serialVersionUID = -1610467969874691778L;

	private GraphicsStyle style = new GraphicsStyle();

	public static final int PANEL_WIDTH = 500;
	public static final int PANEL_HEIGHT = 500;
	public static final int tileWidth = PANEL_WIDTH / 3;
	public static final int tileHeight = PANEL_HEIGHT / 3;

	public RenderService() {
		super(60); // framerate

		setGraphicsStyle(style.setStyle(Style.COLOR, Color.gray).setStyle(Style.BUTTON_HOVERED, Color.gray.brighter())
				.setStyle(Style.BUTTON_SELECTED, Color.gray.brighter().brighter()).setStyle(Style.STROKE, Color.black)
				.setStyle(Style.FONT, Fonts.arial(12)));

		InputController.registerClickInput(this);
	}

	@Override
	public void drawGUI() {
		for (int x = 0; x < TicTacToe.getBoard().getTiles().length; x++) {
			for (int y = 0; y < TicTacToe.getBoard().getTileColumn(x).length; y++) {
				Tile tile = TicTacToe.getBoard().getTile(x, y);

				if (tile != Tile.EMPTY) {
					GraphicsStyle override = style.clone().setStyle(Style.COLOR, Color.blue)
							.setStyle(Style.STROKE_WIDTH, 10);
					Shape shape = null;
					switch (tile) {
					case X:
						break;
					case O:
						shape = new Ellipse((x * tileWidth) + ((tileWidth - (int) (tileWidth / 1.1)) / 2),
								(y * tileHeight) + ((tileHeight - (int) (tileHeight / 1.1)) / 2),
								(int) (tileWidth / 1.1), (int) (tileHeight / 1.1));
						break;
					default:
						break;
					}
					drawShape(new Rectangle(x * tileWidth, y * tileHeight, tileWidth, tileHeight));
					if (shape != null) {
						drawShape(shape, override, false, true);
						drawShape(shape, override.setStyle(Style.STROKE_WIDTH, 1), false, true);
					}
				} else {
					drawButton(new Rectangle(x * tileWidth, y * tileHeight, tileWidth, tileHeight));
				}
			}
		}
	}
}
