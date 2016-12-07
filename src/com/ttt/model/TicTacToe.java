package com.ttt.model;

import javax.swing.JFrame;

import com.ttt.control.GameController;
import com.ttt.view.MenuService;
import com.ttt.view.RenderService;
import com.ttt.view.WindowIcon;

public class TicTacToe {
	public static JFrame FRAME;
	public static Board board;

	public static void main(String[] args) {
		board = new Board();
		setupGraphics();

		Brain first = null;
		Brain second = null;
		Player player1 = new Player("", first);
		Player player2 = new Player("", second);
		GameController game = new GameController(player1, player2, board);
		Player winner = game.playGame();
		System.out.println(winner.getLabel() + " won!");
	}

	public static void setupGraphics() {
		FRAME = new JFrame("Tic Tac Toe");
		FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WindowIcon.setupIcons(FRAME);
		FRAME.getContentPane().add(new MenuService().getContents());
		FRAME.setVisible(true);
		FRAME.setSize(RenderService.PANEL_WIDTH, RenderService.PANEL_HEIGHT + FRAME.getInsets().top);
	}

	public static void returnToMainMenu() {
		FRAME.getContentPane().removeAll();
		FRAME.getContentPane().add(new MenuService().getContents());
		FRAME.revalidate();
		FRAME.repaint();
	}

	public static Board getBoard() {
		return board;
	}

	public static void setBoard(Board board) {
		TicTacToe.board = board;
	}
}
