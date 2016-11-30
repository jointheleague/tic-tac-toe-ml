package com.ttt.model;

import javax.swing.JFrame;

import com.ttt.control.GameController;
import com.ttt.control.SimulationController;
import com.ttt.view.MenuService;
import com.ttt.view.RenderService;
import com.ttt.view.WindowIcon;

public class TicTacToe {
	public static JFrame FRAME;
	private static Board board;

	public static void main(String[] args) {
		board = new Board();
		setupGraphics(board);
	}

	public static void playGame() {
		// Player a = new AIPlayer("X's", SimulationController.learn(100,
		// 10).setTile(Tile.X));
		Player a = new HumanPlayer("X's");
		System.out.println("Learning...");
		Player b = new AIPlayer("O's", SimulationController.learn(1000, 100).setTile(Tile.O));
		System.out.println("Completed learning.");

		GameController controller = new GameController(a, b, board);
		System.out.println(controller.playGame().getLabel() + " won!");
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
