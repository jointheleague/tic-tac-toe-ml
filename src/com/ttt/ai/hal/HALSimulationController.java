package com.ttt.ai.hal;

import com.ttt.control.SimulationController;
import com.ttt.model.Brain;
import com.ttt.model.Tile;

public class HALSimulationController implements SimulationController {
	@Override
	public Brain getAI(Tile tile) {
		return new HALBrain();
	}
}
