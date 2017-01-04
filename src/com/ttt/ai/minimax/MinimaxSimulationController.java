package com.ttt.ai.minimax;

import com.ttt.control.SimulationController;
import com.ttt.model.Brain;
import com.ttt.model.Tile;

public class MinimaxSimulationController implements SimulationController {
	@Override
	public Brain getAI(Tile tile) {
		return new Minimax(2, tile);
	}
}
