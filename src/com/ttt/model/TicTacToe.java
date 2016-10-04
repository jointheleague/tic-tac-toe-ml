package com.ttt.model;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.ttt.view.RenderService;

public class TicTacToe {
	
	public static Tile[][] tiles = new Tile[][]{
		{ Tile.EMPTY, Tile.O, Tile.EMPTY},
		{ Tile.EMPTY, Tile.O, Tile.EMPTY},
		{ Tile.EMPTY, Tile.O, Tile.EMPTY}
	};

	public static void main(String[] args) {
		Board board = new Board(tiles);
		
		//Testing the board solved state
	    System.out.println("Has won : " + board.checkWin(Tile.O));

		JFrame frame = new JFrame("Tic Tac Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon img = new ImageIcon("res/Icon.png");
		frame.setIconImage(img.getImage());
		frame.add(new RenderService(board).getContents());
		frame.setVisible(true);
		frame.setSize(RenderService.PANEL_WIDTH, RenderService.PANEL_HEIGHT + frame.getInsets().top);
	}
}
