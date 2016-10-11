package com.ttt.model;

import java.awt.Image;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.ttt.view.RenderService;
import com.ttt.view.WindowIcon;

public class TicTacToe {

	public static Tile[][] tiles = new Tile[][] {
			{ Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
			{ Tile.EMPTY, Tile.EMPTY, Tile.EMPTY },
			{ Tile.EMPTY, Tile.EMPTY, Tile.EMPTY } };

	public static void main(String[] args) {
		Board board = new Board(tiles);

		// Testing the board solved state
		System.out.println("Has won : " + board.checkWin(Tile.O));

		setupGraphics(board);
	}

	public static void setupGraphics(Board b) {
		JFrame frame = new JFrame("Tic Tac Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WindowIcon.setupIcons(frame);
		frame.add(new RenderService(b).getContents());
		frame.setVisible(true);
		frame.setSize(RenderService.PANEL_WIDTH, RenderService.PANEL_HEIGHT + frame.getInsets().top);
	}
}
