package com.ttt.ai;

import com.ttt.model.Board;

import sun.awt.geom.Crossings;

public class Trainer {

	public static void main(String[] args) {
		JNeuralNetwork nbase = new JNeuralNetwork();
		nbase.addLayer(new JLayer(Board.BOARD_HEIGHT * Board.BOARD_WIDTH + 1));
		nbase.addLayer(new JLayer(15));
		nbase.addLayer(new JLayer(15));
		nbase.addLayer(new JLayer(Board.BOARD_HEIGHT * Board.BOARD_WIDTH));

		nbase.makeWeightGroups();
		nbase.flush();
		
		JNeuralNetwork nbase1 = new JNeuralNetwork();
		nbase1.addLayer(new JLayer(Board.BOARD_HEIGHT * Board.BOARD_WIDTH + 1));
		nbase1.addLayer(new JLayer(15));
		nbase1.addLayer(new JLayer(15));
		nbase1.addLayer(new JLayer(Board.BOARD_HEIGHT * Board.BOARD_WIDTH));

		nbase1.makeWeightGroups();
		nbase1.flush();
		
		Population pop = new Population(nbase, 100, 0.01, 3);

		pop.setDebug(false);
		pop.setExtraDebug(false);
		pop.setMaxDepth(10); // Dosen't matter for 3 x 3 board
		for (int i = 0; i < 1000; i++) {
			pop.runGeneration();
			System.out.println(pop.getOutput());
		}

	}

}
