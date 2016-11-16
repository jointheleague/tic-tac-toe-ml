package com.ttt.control;

import com.ttt.model.Board;
import com.ttt.model.HumanPlayer;
import com.ttt.model.Player;
import com.ttt.model.TicTacToe;

public class HumanVsHumanSim extends SimulationController{
	
	@Override
	public void simulate(){
		while(simActive){
		TicTacToe.board = new Board();
		Player p1 = new HumanPlayer("Player 1");
		Player p2 = new HumanPlayer("Player 2");
		Player winner = new GameController(p1, p2, TicTacToe.board).playGame();
		System.out.print(winner.getLabel() + " Won!");
		TicTacToe.returnToMainMenu();
		}
	}

}
