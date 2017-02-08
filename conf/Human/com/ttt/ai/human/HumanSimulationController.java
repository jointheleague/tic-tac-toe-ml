package com.ttt.ai.human;

import com.ttt.control.SimulationController;
import com.ttt.input.InputController;
import com.ttt.model.Brain;
import com.ttt.model.Tile;
import com.ttt.model.TilePosition;

public class HumanSimulationController implements SimulationController {
	@Override
	public Brain getAI(Tile tile) {
		return new Brain() {
			@Override
			public TilePosition getNextMove(Tile[][] tiles) {
				synchronized (InputController.clicked) {
					try {
						InputController.clicked.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				return new TilePosition(tile, InputController.tileX, InputController.tileY);
			}
		};
	}
}
