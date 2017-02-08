package com.ttt.ai.rando;

import java.util.ArrayList;
import java.util.Random;

import com.ttt.model.Brain;
import com.ttt.model.Tile;
import com.ttt.model.TilePosition;

public class Rando implements Brain {
	private static final Random random = new Random();

	@Override
	public TilePosition getNextMove(Tile[][] tiles) {
		ArrayList<TilePosition> availible = new ArrayList<TilePosition>();
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[0].length; y++) {
				if (tiles[x][y] == Tile.EMPTY) {
					availible.add(new TilePosition(x, y));
				}
			}
		}

		return availible.get(random.nextInt(availible.size()));
	}
}
