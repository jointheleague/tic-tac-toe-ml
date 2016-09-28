package com.ttt.model;

public class Board {

	//TODO catch if width/height is not positive
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
	
	public TilePosition checkSurroundingTiles(Tile tile, int i, int j, int dirX, int dirY){
		if(tiles[i + dirX][j + dirY] == tile){
			return new TilePosition(tile, i + 1, j);
		}else{
		return new TilePosition(Tile.EMPTY, -1, -1);
		}
	}
	
	public int getPathLength(Tile tile, int dirX, int dirY){
		
		return 0;
	}
	
	public boolean checkWin(Tile tile){
		int consecutiveCount = 0;
		for(int i = 0; i < BOARD_WIDTH; i++){
			for(int j = 0; j < BOARD_HEIGHT; j++){
					if(tile == checkSurroundingTiles(tile, i, j, 1, 0).getTile()){
						getPathLength(tile, 1, 0);
					}else if(tile == checkSurroundingTiles(tile, i, j, 0, 1).getTile()){
						getPathLength(tile, 0, 1);
						}else if(tile == checkSurroundingTiles(tile, i, j, 1, 1).getTile()){
							getPathLength(tile, 1, 1);
						}else if(tile == checkSurroundingTiles(tile, i, j, 0, -1).getTile()){
							getPathLength(tile, 0, -1);
						}else if(tile == checkSurroundingTiles(tile, i, j, -1, -1).getTile()){
							getPathLength(tile, -1, -1);
						}
						consecutiveCount++;
					}
						consecutiveCount = 0;
					}
				
		
		if(consecutiveCount >= WIN_COUNT){
			return true;
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
