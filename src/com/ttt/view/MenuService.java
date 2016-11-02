package com.ttt.view;

import java.awt.Color;

import javax.awt.Fonts;
import javax.awt.GUIApplication;
import javax.awt.GraphicsStyle;
import javax.awt.Rectangle;
import javax.awt.Style;

import com.ttt.model.TicTacToe;

public class MenuService extends GUIApplication {
	private static final long serialVersionUID = -3269621559758222014L;

	private GraphicsStyle style = new GraphicsStyle();

	public MenuService() {
		super(60);
		setGraphicsStyle(style.setStyle(Style.COLOR, Color.gray).setStyle(Style.BUTTON_HOVERED, Color.gray.brighter())
				.setStyle(Style.BUTTON_SELECTED, Color.gray.brighter().brighter()).setStyle(Style.STROKE, Color.black)
				.setStyle(Style.FONT, Fonts.arial(12)));
	}

	@Override
	public void drawGUI() {
		if (drawButton(new Rectangle(125, 0, 250, 75), "Player vs. Player")) {
			clear();
			TicTacToe.FRAME.getContentPane().add(new RenderService().getContents());
			refresh();
			return;
		}
		style.setStyle(Style.COLOR, Color.lightGray);
		if (drawShape(new Rectangle(125, 75, 250, 75), "Player vs. AI")) {
		}
		if (drawShape(new Rectangle(125, 150, 250, 75), "AI vs. AI")) {
		}
		style.setStyle(Style.COLOR, Color.gray);
	}

	private boolean drawShape(Rectangle shape, String text) {
		drawShape(shape);

		int centerX = shape.getX() + (shape.getWidth() / 2)
				- (getContents().getGraphics().getFontMetrics().stringWidth(text) / 2);
		int centerY = shape.getY() + (shape.getHeight() / 2)
				+ (getContents().getGraphics().getFontMetrics().getHeight() / 4);
		drawLabel(centerX, centerY, text);

		return false;
	}

	private void refresh() {
		TicTacToe.FRAME.revalidate();
		TicTacToe.FRAME.repaint();
	}

	private void clear() {
		TicTacToe.FRAME.getContentPane().removeAll();
	}
}
