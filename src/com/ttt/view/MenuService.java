package com.ttt.view;

import java.io.File;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.ttt.TicTacToe;
import com.ttt.ai.PlayerConfiguration;
import com.ttt.model.Player;
import com.ttt.model.Tile;

public class MenuService extends JPanel {
	private static final long serialVersionUID = -3269621559758222014L;

<<<<<<< HEAD
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
			TicTacToe.clear();
			TicTacToe.FRAME.getContentPane().add(new RenderService().getContents());
			TicTacToe.refresh();
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
=======
	@SuppressWarnings("unchecked")
	public MenuService() throws IOException {
		add(getOptions());
		JButton start = new JButton("Start");
		start.addActionListener((e) -> {
			PlayerConfiguration first = (PlayerConfiguration) ((JComboBox<PlayerConfiguration>) getComponent(0))
					.getSelectedItem();
			PlayerConfiguration second = (PlayerConfiguration) ((JComboBox<PlayerConfiguration>) getComponent(2))
					.getSelectedItem();

			TicTacToe.FRAME.getContentPane().removeAll();
			TicTacToe.FRAME.getContentPane().add(new RenderService().getContents());
			TicTacToe.FRAME.revalidate();
			TicTacToe.FRAME.repaint();
			new Thread(new Runnable() {
				@Override
				public void run() {
					TicTacToe.play(
							new Player(first.getPlayerName(), Tile.X, first.getSimulationController().getAI(Tile.X)),
							new Player(second.getPlayerName(), Tile.O, second.getSimulationController().getAI(Tile.O)));
				}
			}).start();

		});
		add(start);
		add(getOptions());
	}

	private JComboBox<PlayerConfiguration> getOptions() throws IOException {
		JComboBox<PlayerConfiguration> options = new JComboBox<>(new PlayerConfiguration[] {});
		DefaultComboBoxModel<PlayerConfiguration> model = (DefaultComboBoxModel<PlayerConfiguration>) options
				.getModel();
		for (File file : new File("conf").listFiles()) {
			model.addElement(PlayerConfiguration.load(file));
		}
		return options;
>>>>>>> master
	}
}
