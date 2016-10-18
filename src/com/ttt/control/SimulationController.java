package com.ttt.control;

import com.ttt.model.Player;
import com.ttt.model.TicTacToe;

public class SimulationController {
	
	//How many generations to simulate
	public static final int SIM_GENS = 100;
	
	public static void simulate(){
		for(int i = 0; i < SIM_GENS; i++){
			TicTacToe.getBoard().clearBoard();
			//TODO: Create new GameControllers() here and play a series of games...
		}
	}

}
