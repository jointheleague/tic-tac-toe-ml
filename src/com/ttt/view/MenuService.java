package com.ttt.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.ttt.TicTacToe;
import com.ttt.ai.PlayerConfiguration;
import com.ttt.model.Player;
import com.ttt.model.Tile;

public class MenuService extends JPanel {
	private static final long serialVersionUID = -3269621559758222014L;

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

		JButton pull = new JButton("Pull");
		pull.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog pullDialog = new JDialog(TicTacToe.FRAME, "Pull AI");
				pullDialog.add(new PullService(pullDialog));
				pullDialog.setVisible(true);
				pullDialog.pack();
			}
		});
		add(pull);
	}

	private JComboBox<PlayerConfiguration> getOptions() throws IOException {
		JComboBox<PlayerConfiguration> options = new JComboBox<>(new PlayerConfiguration[] {});
		DefaultComboBoxModel<PlayerConfiguration> model = (DefaultComboBoxModel<PlayerConfiguration>) options
				.getModel();
		for (File file : new File("conf").listFiles()) {
			model.addElement(PlayerConfiguration.load(file));
		}
		return options;
	}
}
