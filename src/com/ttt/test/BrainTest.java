package com.ttt.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.ttt.TicTacToe;
import com.ttt.model.Brain;
import com.ttt.model.Tile;
import com.ttt.model.TilePosition;
import com.ttt.pull.LocalImportService;

public class BrainTest {

	@Test
	public void testRando() {
		TicTacToe.register();
		Tile[][] tiles = new Tile[][] { { Tile.EMPTY, Tile.O, Tile.O }, { Tile.O, Tile.O, Tile.O },
				{ Tile.O, Tile.O, Tile.O } };
		Brain rando = LocalImportService.getController("Rando").getAI(Tile.X);
		TilePosition tp = rando.getNextMove(tiles);

		assertNotEquals(tp, null);
		assertEquals(tp.getX(), 0);
		assertEquals(tp.getY(), 0);
	}
}
