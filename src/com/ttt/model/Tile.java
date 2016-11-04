package com.ttt.model;

public enum Tile {
	EMPTY(0),
	X(1),
	O(-1);
	
	private int value;
	private Tile(int val){
		this.value = val;
	}
	
	public int getValue(){
		return value;
	}
}
