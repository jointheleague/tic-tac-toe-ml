package com.ttt.model;

import javax.swing.JFrame;

import com.ttt.view.RenderService;

public class TicTacToe {
<<<<<<< HEAD
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
=======
	public static void main(String[] args) {
		Board board = new Board();

		JFrame frame = new JFrame("Tic Tac Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new RenderService(board).getContents());
		frame.setVisible(true);
		frame.setSize(RenderService.PANEL_WIDTH, RenderService.PANEL_HEIGHT + frame.getInsets().top);
>>>>>>> refs/remotes/origin/master
	}
}
