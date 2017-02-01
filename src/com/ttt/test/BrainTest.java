package com.ttt.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.ttt.ai.rando.RandoSimulationController;
import com.ttt.model.Brain;
import com.ttt.model.Tile;
import com.ttt.model.TilePosition;

public class BrainTest {

	@Test
	public void testRando() {
		Tile[][] tiles = new Tile[][] { { Tile.EMPTY, Tile.O, Tile.O }, { Tile.O, Tile.O, Tile.O },
				{ Tile.O, Tile.O, Tile.O } };
		Brain rando = new RandoSimulationController().getAI(Tile.X);
		TilePosition tp = rando.getNextMove(tiles);

		assertNotEquals(tp, null);
		assertEquals(tp.getX(), 0);
		assertEquals(tp.getY(), 0);
	}
}
