package com.ttt.model;

public class Board {

	// TODO catch if width/height is not positive
	public static final int BOARD_WIDTH = 3;
	public static final int BOARD_HEIGHT = 3;
	public static final int WIN_COUNT = 3;
	public Tile[][] tiles;

	public Board() {
		tiles = emptyBoard();
	}

	public Board(Tile[][] tiles) {
		if (tiles.length > 0) {
			if (tiles.length == BOARD_HEIGHT && tiles[0].length == BOARD_WIDTH) {
				this.tiles = tiles;
			} else {
				System.err.println("ERROR: Parameter tile array has size different from width & height.  -Ruoya");
				System.exit(0);
			}
		} else {
			System.err.println("ERROR: Parameter tile array has size zero.  -Ruoya");
			System.exit(0);
		}
	}

	public TilePosition checkSurroundingTiles(Tile tile, int i, int j, int dirX, int dirY) {
		if (tiles[i + dirX][j + dirY] == tile) {
			return new TilePosition(tile, i + dirX, j + dirY);
		} else {
			return new TilePosition(Tile.EMPTY, -1, -1);
		}
	}

	public int getPathLength(TilePosition tile, int dirX, int dirY) {
		if(dirX == 0 && dirY == 0){
			System.err.println("ERROR: Both directions can't be 0 -Matthew");
			return 1;
		}
		int pathLength = 1;
		TilePosition toCheck = tile;
		while (true) {
			try {
				toCheck = checkSurroundingTiles(toCheck.getTile(), toCheck.getX(), toCheck.getY(), dirX, dirY);
				if (toCheck.getTile() == tile.getTile()) {
					pathLength++;
				} else {
					break;
				}
			} catch (Exception e) {
				break;
			}
		}
		System.out.println("Path length : " + pathLength);
		return pathLength;
	}

	public boolean checkWin(Tile tile) {
		int consecutiveCount = 0;
		for (int i = 0; i < BOARD_WIDTH; i++) {
			for (int j = 0; j < BOARD_HEIGHT; j++) {
				Tile t1 = null, t2 = null, t3 = null, t4 = null;
				try {
					t1 = checkSurroundingTiles(tile, i, j, 1, 0).getTile();
				} catch (Exception e) {

				}
				try {
					t2 = checkSurroundingTiles(tile, i, j, 0, 1).getTile();
				} catch (Exception e) {

				}
				try {
					t3 = checkSurroundingTiles(tile, i, j, 1, 1).getTile();
				} catch (Exception e) {

				}
				try {
					t4 = checkSurroundingTiles(tile, i, j, -1, 1).getTile();
				} catch (Exception e) {

				}
				if (tile == t1) {
					consecutiveCount = getPathLength(new TilePosition(tile, i, j), 1, 0);
				} else if (tile == t2) {
					consecutiveCount = getPathLength(new TilePosition(tile, i, j), 0, 1);
				} else if (tile == t3) {
					consecutiveCount = getPathLength(new TilePosition(tile, i, j), 1, 1);
				} else if (tile == t4) {
					consecutiveCount = getPathLength(new TilePosition(tile, i, j), -1, 1);
				}else
				if (consecutiveCount >= WIN_COUNT) {
					return true;
				}
			}
		}
		return false;
	}

	public void placeXAt(int x, int y) {
		try {
			tiles[x][y] = Tile.X;
		} catch (ArrayIndexOutOfBoundsException exception) {
			System.err.println("ERROR: Tried placing X outside of tile array.  -Ruoya");
			System.exit(0);
		}
	}

	public void placeOAt(int x, int y) {
		try {
			tiles[x][y] = Tile.O;
		} catch (ArrayIndexOutOfBoundsException exception) {
			System.err.println("ERROR: Tried placing O outside of tile array.  -Ruoya");
			System.exit(0);
		}
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
