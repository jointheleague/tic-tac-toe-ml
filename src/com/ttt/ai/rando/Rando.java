package com.ttt.ai.rando;

import java.util.Random;

import com.ttt.model.Brain;
import com.ttt.model.Tile;
import com.ttt.model.TilePosition;

public class Rando implements Brain {
	private static final Random random = new Random();
	@Override
	public TilePosition getNextMove(Tile[][] tiles) {
		return new TilePosition(random.nextInt(3), random.nextInt(3));
	}
}
