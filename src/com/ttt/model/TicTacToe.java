package com.ttt.model;

import javax.swing.JFrame;

import com.ttt.control.GameController;
import com.ttt.control.HumanVsHumanSim;
import com.ttt.view.MenuService;
import com.ttt.view.RenderService;
import com.ttt.view.WindowIcon;

public class TicTacToe {
	public static JFrame FRAME;
	public static Board board;

	public static void main(String[] args) {
		board = new Board();
		setupGraphics();
		new HumanVsHumanSim().startSimulation();
	}
	
	public static void setupGraphics() {
		FRAME = new JFrame("Tic Tac Toe");
		FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WindowIcon.setupIcons(FRAME);
		FRAME.getContentPane().add(new MenuService().getContents());
		FRAME.setVisible(true);
		FRAME.setSize(RenderService.PANEL_WIDTH, RenderService.PANEL_HEIGHT + FRAME.getInsets().top);
	}
	
	public static void returnToMainMenu(){
		clear();
		FRAME.getContentPane().add(new MenuService().getContents());
		refresh();
	}

	public static Board getBoard() {
		return board;
	}

	public static void setBoard(Board board) {
		TicTacToe.board = board;
	}
	
	public static void refresh() {
		FRAME.revalidate();
		FRAME.repaint();
	}

	public static void clear() {
		FRAME.getContentPane().removeAll();
	}
}
