package com.ttt.model;

import com.ttt.control.TileOverrideException;

public class Board {
	public static final int BOARD_WIDTH = 3;
	public static final int BOARD_HEIGHT = 3;
	public static final int WIN_COUNT = 3;
	private Tile currentTurn = Tile.X;
	private Tile[][] tiles;

	public Board() {
		this.tiles = emptyBoard();
		this.tiles = getTilesClone();
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

	public Tile getTurn() {
		return currentTurn;
	}

	@Deprecated
	public void switchTurn() {
		// TODO: Remove method
		if (currentTurn == Tile.X) {
			currentTurn = Tile.O;
		} else {
			currentTurn = Tile.X;
		}
		System.err.println("Board.switchTurn() is deprecated, use GameControllers to manage games from now on!");
	}

	public Tile getTile(int x, int y) {
		if (!(x >= BOARD_WIDTH || x < 0)) {
			if (!(y >= BOARD_HEIGHT || y < 0)) {
				return tiles[x][y];
			}
		}
		return null;
	}

	public void setTile(int x, int y, Tile t) throws TileOverrideException {
		if (!(x >= BOARD_WIDTH || x < 0)) {
			if (!(y >= BOARD_HEIGHT || y < 0)) {
				if((tiles[x][y] == Tile.EMPTY)){
				tiles[x][y] = t;
				}else{
					throw new TileOverrideException();
				}
			}
		}
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public Tile[][] getTilesClone() {
		Tile[][] newTiles = new Tile[BOARD_WIDTH][BOARD_HEIGHT];
		for (int x = 0; x < BOARD_WIDTH; x++) {
			for (int y = 0; y < BOARD_HEIGHT; y++) {
				newTiles[x][y] = this.tiles[x][y];
			}
		}
		return newTiles;
	}

	public Tile[] getTileColumn(int x) {
		return tiles[x];
	}

	private TilePosition checkSurroundingTiles(Tile tile, int i, int j, int dirX, int dirY) {
		if (getTile(i + dirX, j + dirY) == tile) {
			return new TilePosition(tile, i + dirX, j + dirY);
		} else {
			return new TilePosition(Tile.EMPTY, -1, -1);
		}
	}

	private int getPathLength(TilePosition tile, int dirX, int dirY) {
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

	@Deprecated
	public void placeAt(int x, int y, Tile tile) {
		//setTile(x, y, tile);
		System.err.println("Baord.placeAt() is deprecated, please use setTile instead.");
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
