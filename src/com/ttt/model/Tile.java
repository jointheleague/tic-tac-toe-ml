package com.ttt.model;

public enum Tile {
	EMPTY(0), X(1), O(-1);

	private int v;

	Tile(int v) {
		this.v = v;
	}

	public int asInt() {
		return v;
	}
}
