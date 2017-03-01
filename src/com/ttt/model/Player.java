package com.ttt.model;

import com.ttt.control.TileOverrideException;

public class Player {
	private String playerLabel;
	private Tile tileType;
	private Brain ai;

	public Player(String label, Tile tile, Brain ai) {
		this.playerLabel = label;
		this.tileType = tile;
		this.ai = ai;
	}

	public String getLabel() {
		return playerLabel;
	}

	public void swapTileType() {
		if (tileType == Tile.EMPTY) {
			System.err.println("Cannot Swap TileType From Tile State : Empty");
			return;
		}
		tileType = (tileType == Tile.X ? Tile.O : Tile.X);
	}

	public Tile getTileType() {
		return tileType;
	}

	public void performTurn(Board board) throws TileOverrideException{
		TilePosition tile = ai.getNextMove(board.getTilesClone());
		if (board.getTile(tile.getX(), tile.getY()) != Tile.EMPTY) {
			performTurn(board);
		} else {
			board.setTile(tile.getX(), tile.getY(), this.getTileType());
		}
	}
}
