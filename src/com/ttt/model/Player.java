package com.ttt.model;

public class Player {
	
	String playerLabel;
	Tile tileType = Tile.X;

	public Player(String label){
		this.playerLabel = label;
	}
	
	public String getLabel(){
		return playerLabel;
	}
	
	public void performTurn(Board b){
		
	}
	public void swapTileType(){
		if(tileType == Tile.EMPTY){
			System.err.println("Cannot Swap TileType From Tile State : Empty");
			return;
		}
		tileType = (tileType == Tile.X ? Tile.O : Tile.X);
	}
	public Tile getTileType(){
		return tileType;
	}

}
