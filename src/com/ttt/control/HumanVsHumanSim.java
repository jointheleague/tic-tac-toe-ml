package com.ttt.control;

import javax.swing.JOptionPane;

import com.ttt.model.Board;
import com.ttt.model.HumanPlayer;
import com.ttt.model.Player;
import com.ttt.model.TicTacToe;

public class HumanVsHumanSim extends SimulationController {

	@Override
	public void simulate() {
		while (simActive) {
			TicTacToe.board = new Board();
			Player p1 = new HumanPlayer("Player 1");
			Player p2 = new HumanPlayer("Player 2");
			Player winner = new GameController(p1, p2, TicTacToe.board).playGame();
			System.out.println(winner.getLabel() + " Won!");
			if (winner instanceof HumanPlayer) {
				JOptionPane.showMessageDialog(null, winner.getLabel() + " Won");
			} else {
				JOptionPane.showMessageDialog(null, "Draw!");
			}
			TicTacToe.returnToMainMenu();
		}
	}
}
