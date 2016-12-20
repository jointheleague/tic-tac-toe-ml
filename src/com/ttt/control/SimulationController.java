package com.ttt.control;

import com.ttt.model.Brain;
import com.ttt.model.Tile;

public interface SimulationController {
	public Brain getAI(Tile tile);
}
