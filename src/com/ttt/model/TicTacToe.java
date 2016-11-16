package com.ttt.model;

import javax.swing.JFrame;

import com.ttt.ai.Individual;
import com.ttt.ai.Minimax;
import com.ttt.ai.TrainedPlayer;
import com.ttt.control.GameController;
import com.ttt.view.RenderService;
import com.ttt.view.WindowIcon;

public class TicTacToe {

	private static Board board;

	public static void main(String[] args) {
		board = new Board();
		Player p2 = new HumanPlayer("Player 1");
		AIPlayer mini = new AIPlayer("AI Minimax");
		Minimax minimax = new Minimax(10, Tile.X);
		mini.setNetwork(minimax);
		setupGraphics(board);
		Player winner = new GameController(p2, mini, board).playGame();
		System.out.print(winner.getLabel() + " Won!");
	}

	public static void setupGraphics(Board b) {
		JFrame frame = new JFrame("Tic Tac Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WindowIcon.setupIcons(frame);
		frame.add(new RenderService().getContents());
		frame.setVisible(true);
		frame.setSize(RenderService.PANEL_WIDTH, RenderService.PANEL_HEIGHT + frame.getInsets().top);
	}

	public static Board getBoard() {
		return board;
	}

	public static void setBoard(Board board) {
		TicTacToe.board = board;
	}
}
