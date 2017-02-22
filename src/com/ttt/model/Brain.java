package com.ttt.model;

import com.ttt.control.TileOverrideException;

public interface Brain {
	public TilePosition getNextMove(Tile[][] tiles) throws TileOverrideException;
}
