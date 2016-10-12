package com.ttt.model;

public class Board {

	// TODO catch if width/height is not positive
	public static final int BOARD_WIDTH = 3;
	public static final int BOARD_HEIGHT = 3;
	public static final int WIN_COUNT = 3;
	private static Tile currentTurn = Tile.X;
	private Tile[][] tiles;

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
	
	public Tile getTurn(){
		return currentTurn;
	}
	
	public void switchTurn(){
		if(currentTurn == Tile.X){
			currentTurn = Tile.O;
		}else{
			currentTurn = Tile.X;
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
		System.out.println("Path length : " + pathLength);
		return pathLength;
	}

	public boolean checkWin(Tile tile) {
		int consecutiveCount = 0;
		for (int i = 0; i < BOARD_WIDTH; i++) {
			for (int j = 0; j < BOARD_HEIGHT; j++) {
				if(getTile(i, j) == tile){
				if (tile == checkSurroundingTiles(tile, i, j, 1, 0).getTile()) {
					consecutiveCount = getPathLength(new TilePosition(tile, i, j), 1, 0);
					if (consecutiveCount >= WIN_COUNT) return true;
				}
				if (tile == checkSurroundingTiles(tile, i, j, 0, 1).getTile()) {
					consecutiveCount = getPathLength(new TilePosition(tile, i, j), 0, 1);
					if (consecutiveCount >= WIN_COUNT) return true;
				}
				if (tile == checkSurroundingTiles(tile, i, j, 1, 1).getTile()) {
					consecutiveCount = getPathLength(new TilePosition(tile, i, j), 1, 1);
					if (consecutiveCount >= WIN_COUNT) return true;
				}
				if (tile == checkSurroundingTiles(tile, i, j, -1, 1).getTile()) {
					consecutiveCount = getPathLength(new TilePosition(tile, i, j), -1, 1);
					if (consecutiveCount >= WIN_COUNT) return true;
				}
				}
				if (consecutiveCount >= WIN_COUNT) {
					return true;
				}
			}
		}
		return false;
	}

	public void placeAt(int x, int y, Tile tile) {
		setTile(x, y, tile);
	}

	public Tile[][] emptyBoard() {
		Tile[][] tiles = new Tile[BOARD_WIDTH][BOARD_HEIGHT];
		for (int x = 0; x < BOARD_WIDTH; x++) {
			for (int y = 0; y < BOARD_HEIGHT; y++) {
				setTile(x, y, Tile.EMPTY);
			}
		}
		return tiles;
	}

}
