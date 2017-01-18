package com.ttt.model;

public enum Tile {
	EMPTY(0),
	X(1),
	O(-1);
	
	private int value;
	Tile(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
}
