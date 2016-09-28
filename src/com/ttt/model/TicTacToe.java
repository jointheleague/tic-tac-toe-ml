package com.ttt.model;

import javax.swing.JFrame;

import com.ttt.view.RenderService;

public class TicTacToe {
	public static void main(String[] args) {
		Board board = new Board();

		JFrame frame = new JFrame("Tic Tac Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new RenderService(board).getContents());
		frame.setVisible(true);
		frame.setSize(RenderService.PANEL_WIDTH - 1, RenderService.PANEL_HEIGHT + frame.getInsets().top - 1);
	}
}
