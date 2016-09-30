package com.ttt.model;
public class TicTacToe {
	//Main method
	public static void main(String[] args){
		
		Tile[][] test = new Tile[][]{
			{Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY},
			{Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.EMPTY},
			{Tile.EMPTY, Tile.X, Tile.EMPTY, Tile.O, Tile.EMPTY},
			{Tile.EMPTY, Tile.EMPTY, Tile.X, Tile.O, Tile.EMPTY},
			{Tile.EMPTY, Tile.EMPTY, Tile.EMPTY, Tile.X, Tile.EMPTY}
		};
		
		Board board = new Board(test);
		System.out.println(board.checkWin(Tile.EMPTY));
	}
}
