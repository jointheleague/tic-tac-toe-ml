package com.ttt.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.rules.ExpectedException;

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
	
	@Test
	public void testBoardGetterSetter() {
		Tile[][] tiles = new Tile[][] { { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
			{ Tile.EMPTY, Tile.O, Tile.EMPTY }, { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY } };
		Board b = new Board(tiles);
		
		assertEquals(b.getTile(1, 1), Tile.O);
		assertEquals(b.getTile(0, 0), Tile.EMPTY);
		assertEquals(b.getTile(-1, 0), null);
		b.setTile(5, 5, Tile.EMPTY);
	}

	@Test
	public void testBoardCheckWin() {
		//TODO: Remove printout from checkWin method
		Tile[][] tiles = new Tile[][] { { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
			{ Tile.EMPTY, Tile.EMPTY, Tile.EMPTY }, { Tile.EMPTY, Tile.EMPTY, Tile.EMPTY} };
		Board b = new Board(tiles);
		assertEquals(b.checkWin(Tile.X) || b.checkWin(Tile.O), false);
		
		tiles = new Tile[][] { { Tile.X, Tile.O, Tile.X },
			{ Tile.EMPTY, Tile.O, Tile.X }, { Tile.O, Tile.EMPTY, Tile.O } };
		b = new Board(tiles);
		assertEquals(b.checkWin(Tile.X) || b.checkWin(Tile.O), false);
		
		tiles = new Tile[][] { { Tile.X, Tile.O, Tile.O},
			{ Tile.X, Tile.X, Tile.X }, { Tile.O, Tile.X, Tile.O } };
		b = new Board(tiles);
		assertEquals(b.checkWin(Tile.X), true);
		assertEquals(b.checkWin(Tile.O), false);
		
		tiles = new Tile[][] { { Tile.O, Tile.O, Tile.O},
			{ Tile.O, Tile.O, Tile.O }, { Tile.O, Tile.O, Tile.O } };
		b = new Board(tiles);
		assertEquals(b.checkWin(Tile.X), false);
		assertEquals(b.checkWin(Tile.O), true);
	}

}
