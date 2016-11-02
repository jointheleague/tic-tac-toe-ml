package com.ttt.ai;

import com.ttt.model.Board;

public class Trainer {

	public static void main(String[] args) {
		NeuralNetwork nbase = new NeuralNetwork();
		nbase.setInputLayer(new Layer(Board.BOARD_HEIGHT * Board.BOARD_WIDTH + 1));
		nbase.addLogicLayer(new Layer(100));
		nbase.addLogicLayer(new Layer(100));
		nbase.setOutputLayer(new Layer(Board.BOARD_HEIGHT * Board.BOARD_WIDTH));

		nbase.connectSynapsesBetweenLayers();
		nbase.compute();
		
		Population pop = new Population(nbase, 100, 0.01, 3);

		pop.setDebug(false);
		pop.setExtraDebug(false);
		pop.setMaxDepth(10); //Dosen't matter for 3 x 3 board
		for(int i = 0; i < 1000000
				; i++){
			pop.runGeneration();
			System.out.println(pop.getOutput());
		}

	}

}
