package com.ttt.model;

import javax.swing.JFrame;

import com.ttt.control.GameController;
import com.ttt.view.MenuService;
import com.ttt.view.RenderService;
import com.ttt.view.WindowIcon;

public class TicTacToe {
	public static JFrame FRAME;
	private static Board board;

	public static void main(String[] args) {
		setupGraphics(board);
		while (true) {
			board = new Board();
			Player p1 = new HumanPlayer("Player 1");
			Player p2 = new HumanPlayer("Player 2");
			Player winner = new GameController(p1, p2, board).playGame();
			if (winner == null) {
				System.out.println("Game tied.");
			} else {
				System.out.println(winner.getLabel() + " Won!");
			}
			clear();
			TicTacToe.FRAME.getContentPane().add(new MenuService().getContents());
			refresh();
		}
	}

	public static void refresh() {
		FRAME.setSize(RenderService.PANEL_WIDTH, RenderService.PANEL_HEIGHT + FRAME.getInsets().top);
		TicTacToe.FRAME.revalidate();
		TicTacToe.FRAME.repaint();
	}

	public static void clear() {
		TicTacToe.FRAME.getContentPane().removeAll();
	}
	
	public static void setupGraphics(Board b) {
		FRAME = new JFrame("Tic Tac Toe");
		FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WindowIcon.setupIcons(FRAME);
		FRAME.getContentPane().add(new MenuService().getContents());
		FRAME.setVisible(true);
		FRAME.setSize(RenderService.PANEL_WIDTH, RenderService.PANEL_HEIGHT + FRAME.getInsets().top);
	}

	public static Board getBoard() {
		return board;
	}

	public static void setBoard(Board board) {
		TicTacToe.board = board;
	}
}
