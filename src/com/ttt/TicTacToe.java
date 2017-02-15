package com.ttt;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.ttt.ai.PlayerConfiguration;
import com.ttt.control.GameController;
import com.ttt.model.Board;
import com.ttt.model.Player;
import com.ttt.pull.LocalImportService;
import com.ttt.view.MenuService;
import com.ttt.view.RenderService;
import com.ttt.view.WindowIcon;

public class TicTacToe {
	public static JFrame FRAME;
	public static Board board = new Board();

	static {
		try {
			File confFolder = new File("conf");
			for (File file : confFolder.listFiles()) {
				if (file.isDirectory()) {
					MenuService.configs.add(LocalImportService.handleDirectory(file));
				} else if (file.getName().endsWith(".cfg")) {
					MenuService.configs.add(PlayerConfiguration.load(file));
				}
			}
		} catch (IOException | ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			setupGraphics();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static void play(Player player1, Player player2) throws IOException {
		GameController game = new GameController(player1, player2, board);
		Player winner = game.playGame();
		if (winner == null) {
			System.out.println("Tie!");
		} else {
			System.out.println(winner.getLabel() + " (" + winner.getTileType().name() + "'s) won!");
		}
		returnToMainMenu();
	}

	private static MenuService menu;

	public static void setupGraphics() throws IOException {
		FRAME = new JFrame("Tic Tac Toe");
		FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WindowIcon.setupIcons(FRAME);
		FRAME.getContentPane().add(menu = new MenuService());
		FRAME.setVisible(true);
		FRAME.setSize(RenderService.PANEL_WIDTH, RenderService.PANEL_HEIGHT + FRAME.getInsets().top + 50);
	}

	public static void refreshMenu() throws IOException {
		menu.refresh();
	}

	public static void returnToMainMenu() throws IOException {
		FRAME.getContentPane().removeAll();
		FRAME.getContentPane().add(new MenuService());
		FRAME.revalidate();
		FRAME.repaint();
	}

	public static Board getBoard() {
		return board;
	}

	public static void setBoard(Board board) {
		TicTacToe.board = board;
	}

	public static void register() {
	}
}
