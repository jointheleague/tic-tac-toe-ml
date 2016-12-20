package com.ttt.model;

public class TilePosition {

	private Tile tile;
	private int x;
	private int y;

	public TilePosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public TilePosition(Tile tile, int x, int y) {
		this(x, y);
		this.tile = tile;
	}

	public Tile getTile() {
		return tile;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
