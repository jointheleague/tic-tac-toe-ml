package com.ttt.view;

import java.awt.Color;

import javax.awt.ClickListener;
import javax.awt.Ellipse;
import javax.awt.Fonts;
import javax.awt.GUIApplication;
import javax.awt.GraphicsStyle;
import javax.awt.KeyListener;
import javax.awt.Rectangle;
import javax.awt.Shape;
import javax.awt.Style;

import com.ttt.model.Board;
import com.ttt.model.Tile;

public class RenderService extends GUIApplication {
	private static final long serialVersionUID = -1610467969874691778L;

	private GraphicsStyle style = new GraphicsStyle();
	private Board board;

	public static final int PANEL_WIDTH = 500;
	public static final int PANEL_HEIGHT = 500;
	public static final int tileWidth = PANEL_WIDTH / 3;
	public static final int tileHeight = PANEL_HEIGHT / 3;

	public RenderService(Board board) {
		super(60); // framerate

		this.board = board;

		setGraphicsStyle(style.setStyle(Style.COLOR, Color.gray).setStyle(Style.BUTTON_HOVERED, Color.gray.brighter())
				.setStyle(Style.BUTTON_SELECTED, Color.gray.brighter().brighter()).setStyle(Style.STROKE, Color.black)
				.setStyle(Style.FONT, Fonts.arial(12)));

		setClickListener(new ClickListener() {
			@Override
			public void onClick(int mouseX, int mouseY) {
			}

			@Override
			public void onButtonClick(Shape source, int mouseX, int mouseY) {
				board.tiles[source.getX() / tileWidth][source.getY()
						/ tileHeight] = /* board.getCurrentTurn() */Tile.EMPTY;
			}
		});
		setKeyListener(new KeyListener() {
			@Override
			public void onKeyPress(int keyCode, int mouseX, int mouseY) {
				if (keyCode >= 97 && keyCode <= 105) {
					int keyValue = keyCode - 96;
					int x = keyValue % 3;
					if (x == 0) {
						x = 3;
					}
					x--;
					int y = Math.abs((int) Math.ceil((double) (keyValue - x) / 4) - 2);
					if (keyValue - x == 1) {
						y = 2;
					}

					board.tiles[x][y] = /* board.getCurrentTurn() */Tile.O;
				}
			}
		});

	}

	@Override
	public void drawGUI() {
		for (int x = 0; x < board.tiles.length; x++) {
			for (int y = 0; y < board.tiles[x].length; y++) {
				Tile tile = board.tiles[x][y];

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
