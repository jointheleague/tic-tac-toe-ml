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
		
		Population pop = new Population(nbase, 1000, 0.01, 3);

		pop.setDebug(false);
		pop.setExtraDebug(false);
		pop.setMaxDepth(10); // Dosen't matter for 3 x 3 board
		
		double tiedPercent = 0;
		int gen = 0;
		while(tiedPercent < 80 && gen < 10000) {
			pop.selection();
			pop.makeNewGeneration();
			System.out.println(pop.getOutput());
			tiedPercent = pop.getTiedPercent();
			gen = pop.getGeneration();
			pop.clearStats();
		}

	}

}
