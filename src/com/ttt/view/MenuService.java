package com.ttt.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.ttt.TicTacToe;
import com.ttt.ai.PlayerConfiguration;
import com.ttt.model.Board;
import com.ttt.model.Player;
import com.ttt.model.Tile;
import com.ttt.pull.LocalImportService;

public class MenuService extends JPanel {
	private static final long serialVersionUID = -3269621559758222014L;
	public static ArrayList<PlayerConfiguration> configs = new ArrayList<>();

	public static void registerConfiguration(PlayerConfiguration config) {
		configs.add(config);
	}

	public MenuService() throws IOException {
		refresh();
	}

	private JComboBox<PlayerConfiguration> getOptions() throws IOException {
		JComboBox<PlayerConfiguration> options = new JComboBox<>(new PlayerConfiguration[] {});
		DefaultComboBoxModel<PlayerConfiguration> model = (DefaultComboBoxModel<PlayerConfiguration>) options
				.getModel();
		for (PlayerConfiguration config : configs) {
			model.addElement(config);
		}
		return options;
	}

	@SuppressWarnings("unchecked")
	public void refresh() throws IOException {
		removeAll();
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
					try {
						TicTacToe.play(
								new Player(first.getPlayerName(), Tile.X,
										first.getSimulationController().getAI(Tile.X)),
								new Player(second.getPlayerName(), Tile.O,
										second.getSimulationController().getAI(Tile.O)));
					} catch (IOException e) {
						e.printStackTrace();
					}
					TicTacToe.board = new Board();
				}
			}).start();

		});
		add(start);
		add(getOptions());

		JButton pull = new JButton("Import");
		pull.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog pullDialog = new JDialog(TicTacToe.FRAME, "Import AI");
				pullDialog.add(new PullService(pullDialog));
				pullDialog.setVisible(true);
				pullDialog.pack();
			}
		});
		add(pull);

		revalidate();
		repaint();
	}
}
