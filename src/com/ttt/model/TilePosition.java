package com.ttt.model;

public class TilePosition {
	
	private Tile tile;
	private int x;
	private int y;
	
	public TilePosition(Tile tile, int x, int y){
		this.tile = tile;
		this.x = x;
		this.y = y;
	}
	
	public Tile getTile(){
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
