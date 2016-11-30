package com.ttt.model;

import java.awt.Point;

public class Board {
	// TODO catch if width/height is not positive
	public static final int BOARD_WIDTH = 3;
	public static final int BOARD_HEIGHT = BOARD_WIDTH;
	public static final int WIN_COUNT = BOARD_WIDTH;
	private Tile[][] tiles;

	public Board() {
		tiles = emptyBoard();
	}

	public Board(Board copy) {
		this();

		for (int i = 0; i < copy.tiles.length; i++) {
			for (int j = 0; j < copy.tiles[i].length; j++) {
				tiles[i][j] = copy.tiles[i][j];
			}
		}
	}

	public Board(Tile[][] tiles) {
		if (tiles.length > 0) {
			if (tiles.length == BOARD_HEIGHT && tiles[0].length == BOARD_WIDTH) {
				this.tiles = tiles;
			} else {
				throw new ArrayIndexOutOfBoundsException("Parameter tile array has size different from width & height");
			}
		} else {
			throw new ArrayIndexOutOfBoundsException("Parameter tile array has size zero");
		}
	}

	public Tile getTile(int x, int y) {
		if (!(x >= BOARD_WIDTH || x < 0)) {
			if (!(y >= BOARD_HEIGHT || y < 0)) {
				return tiles[x][y];
			}
		}
		return null;
	}

	public void setTile(int x, int y, Tile t) {
		if (!(x >= BOARD_WIDTH || x < 0)) {
			if (!(y >= BOARD_HEIGHT || y < 0)) {
				tiles[x][y] = t;
			}
		}
	}

	public Point nextEmpty() {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				if (tiles[i][j] == Tile.EMPTY) {
					return new Point(i, j);
				}
			}
		}
		return null;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public Tile[] getTileColumn(int x) {
		return tiles[x];
	}

	public TilePosition checkSurroundingTiles(Tile tile, int i, int j, int dirX, int dirY) {
		if (getTile(i + dirX, j + dirY) == tile) {
			return new TilePosition(tile, i + dirX, j + dirY);
		} else {
			return new TilePosition(Tile.EMPTY, -1, -1);
		}
	}

	public int getPathLength(TilePosition tile, int dirX, int dirY) {
		if (dirX == 0 && dirY == 0) {
			System.err.println("ERROR: Both directions can't be 0 -Matthew");
			return 1;
		}
		int pathLength = 1;
		TilePosition toCheck = tile;
		while (true) {
			toCheck = checkSurroundingTiles(toCheck.getTile(), toCheck.getX(), toCheck.getY(), dirX, dirY);
			if (toCheck.getTile() == tile.getTile()) {
				pathLength++;
			} else {
				break;
			}
		}
		return pathLength;
	}

	public boolean isTie() {
		int unempty = 0;
		for (Tile[] x : tiles) {
			for (Tile y : x) {
				if (y != Tile.EMPTY) {
					unempty++;
				}
			}
		}
		return unempty == BOARD_WIDTH * BOARD_HEIGHT && !checkWin(Tile.X) && !checkWin(Tile.O);
	}

	public boolean checkWin(Tile tile) {
		int consecutiveCount = 0;
		for (int i = 0; i < BOARD_WIDTH; i++) {
			for (int j = 0; j < BOARD_HEIGHT; j++) {
				if (getTile(i, j) == tile) {
					if (tile == checkSurroundingTiles(tile, i, j, 1, 0).getTile()) {
						consecutiveCount = getPathLength(new TilePosition(tile, i, j), 1, 0);
						if (consecutiveCount >= WIN_COUNT)
							return true;
					}
					if (tile == checkSurroundingTiles(tile, i, j, 0, 1).getTile()) {
						consecutiveCount = getPathLength(new TilePosition(tile, i, j), 0, 1);
						if (consecutiveCount >= WIN_COUNT)
							return true;
					}
					if (tile == checkSurroundingTiles(tile, i, j, 1, 1).getTile()) {
						consecutiveCount = getPathLength(new TilePosition(tile, i, j), 1, 1);
						if (consecutiveCount >= WIN_COUNT)
							return true;
					}
					if (tile == checkSurroundingTiles(tile, i, j, -1, 1).getTile()) {
						consecutiveCount = getPathLength(new TilePosition(tile, i, j), -1, 1);
						if (consecutiveCount >= WIN_COUNT)
							return true;
					}
				}
				if (consecutiveCount >= WIN_COUNT) {
					return true;
				}
			}
		}
		return false;
	}

	public void clearBoard() {
		this.tiles = emptyBoard();
	}

	public Tile[][] emptyBoard() {
		Tile[][] tiles = new Tile[BOARD_WIDTH][BOARD_HEIGHT];
		for (int x = 0; x < BOARD_WIDTH; x++) {
			for (int y = 0; y < BOARD_HEIGHT; y++) {
				tiles[x][y] = Tile.EMPTY;
			}
		}
		return tiles;
	}

}
