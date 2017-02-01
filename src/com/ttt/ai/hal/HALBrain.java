package com.ttt.ai.hal;

import com.ttt.model.Brain;
import com.ttt.model.Tile;
import com.ttt.model.TilePosition;

public class HALBrain implements Brain {
	public HALBrain() {
		HALMainController.learn();
	}
	
	@Override
	public TilePosition getNextMove(Tile[][] tiles) {
		return HALMainController.getNextPosition(tiles);
	}
}
