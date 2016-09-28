package com.ttt.view;

import java.awt.Color;

import javax.awt.ClickListener;
import javax.awt.Ellipse;
import javax.awt.Fonts;
import javax.awt.GUIApplication;
import javax.awt.GraphicsStyle;
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
				board.tiles[source.getX() / tileWidth][source.getY() / tileHeight] = Tile.O;
			}
		});
	}

	@Override
	public void drawGUI() {
		for (int x = 0; x < board.tiles.length; x++) {
			for (int y = 0; y < board.tiles[x].length; y++) {
				Tile tile = board.tiles[x][y];
				GraphicsStyle override = style.clone().setStyle(Style.COLOR, Color.black).setStyle(Style.STROKE_WIDTH,
						15);
				Shape shape = null;
				switch (tile) {
				case X:
					break;
				case O:
					shape = new Ellipse(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
					break;
				default:
					break;
				}
				drawButton(new Rectangle(x * tileWidth, y * tileHeight, tileWidth, tileHeight));
				if (shape != null) {
					drawShape(shape, override, false, true);
				}
			}
		}
	}
}
