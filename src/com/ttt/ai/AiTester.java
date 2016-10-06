package com.ttt.ai;

import org.encog.engine.network.activation.ActivationTANH;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.pattern.FeedForwardPattern;

public class AiTester {
	static int tb[][] = { { 0, 0, 1, 0, 0 }, { 0, 0, 1, 0, 0 }, { 0, 0, 1, 0, 0 }, { 0, 0, 1, 0, 0 },
			{ 0, 0, 1, 0, 0 }, };

	static int size = 3;

	public static BasicNetwork createNetwork() {
		FeedForwardPattern pattern = new FeedForwardPattern();
		pattern.setInputNeurons(size);
		pattern.addHiddenLayer(size * 4);
		pattern.addHiddenLayer(size * 4);
		pattern.setOutputNeurons(size);
		pattern.setActivationFunction(new ActivationTANH());
		BasicNetwork network = (BasicNetwork) pattern.generate();
		network.reset();
		return network;
	}

	public static void main(String[] args) {
		TTTSim sim = new TTTSim(3);
		sim.setBoard(tb);
		System.out.println(sim.isWinner(1));

	}
}