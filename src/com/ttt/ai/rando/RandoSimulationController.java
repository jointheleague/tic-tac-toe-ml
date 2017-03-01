package com.ttt.ai.rando;

import com.ttt.control.SimulationController;
import com.ttt.model.Brain;
import com.ttt.model.Tile;

public class RandoSimulationController implements SimulationController {
	@Override
	public Brain getAI(Tile tile) {
		return new Rando();
	}
}
