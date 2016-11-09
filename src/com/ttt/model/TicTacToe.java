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
		board = new Board();
		Player p1 = new HumanPlayer("Player 1");
		Player p2 = new HumanPlayer("Player 2");
		setupGraphics(board);
		Player winner = new GameController(p1, p2, board).playGame();
		System.out.print(winner.getLabel() + " Won!");
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
