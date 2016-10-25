package com.ttt.model;

import com.ttt.input.InputController;

public class HumanPlayer extends Player{

	public HumanPlayer(String label) {
		super(label);
	}
	
	@Override
	public void performTurn(Board b){
		//TODO: Integrate this so that the user can perform a turn as a "Human Player"
		while(!InputController.clicked){
			System.out.println("Waiting...");
		}
		//Turn has been performed on UI thread
		InputController.clicked = false;
		b.setTile(InputController.tileX, InputController.tileY, this.getTileType());
		}
	
	}


