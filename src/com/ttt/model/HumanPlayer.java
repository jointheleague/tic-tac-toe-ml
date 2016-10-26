package com.ttt.model;

import com.ttt.input.InputController;

public class HumanPlayer extends Player {

	public HumanPlayer(String label) {
		super(label);
	}

	@Override
	public void performTurn(Board b) {
		// TODO: Integrate this so that the user can perform a turn as a "Human
		// Player"
		synchronized (InputController.clicked) {
			try {
				InputController.clicked.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Turn has been performed on UI thread
		b.setTile(InputController.tileX, InputController.tileY, this.getTileType());
	}

}
