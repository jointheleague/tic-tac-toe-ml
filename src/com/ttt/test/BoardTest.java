package com.ttt.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ttt.model.Board;
import com.ttt.model.Tile;

public class BoardTest {

	@Test
	public void testBoardInitialization() {
		Board b = new Board();
		for (int x = 0; x < Board.BOARD_WIDTH; x++) {
			for (int y = 0; y < Board.BOARD_HEIGHT; y++) {
				assertEquals(b.getTile(x, y), Tile.EMPTY);
			}
		}
		
		Tile[][] tiles = new Tile[][] { { Tile.O, Tile.EMPTY, Tile.EMPTY },
			{ Tile.EMPTY, Tile.EMPTY, Tile.O }, { Tile.X, Tile.EMPTY, Tile.EMPTY } };
		b = new Board(tiles);
		for (int x = 0; x < Board.BOARD_WIDTH; x++) {
			for (int y = 0; y < Board.BOARD_HEIGHT; y++) {
				if ((x == 0 && y == 0) || (x == 1 && y == 2)) {
					assertEquals(b.getTile(x, y), Tile.O);
				} else if (x == 2 && y == 0) {
					assertEquals(b.getTile(x, y), Tile.X);
				} else {					
					assertEquals(b.getTile(x, y), Tile.EMPTY);
				}
			}
		}
		
	}

}
