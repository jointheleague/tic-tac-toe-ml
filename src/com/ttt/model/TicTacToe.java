package com.ttt.model;
public class TicTacToe {
	//Main method
	public static void main(String[] args){
		
		Tile[][] test = new Tile[][]{
			{Tile.X, Tile.EMPTY, Tile.EMPTY},
			{Tile.EMPTY, Tile.X, Tile.O},
			{Tile.EMPTY, Tile.EMPTY, Tile.X},
		};
		
		Board board = new Board(test);
		System.out.println(board.checkWin(Tile.X));
	}
}
