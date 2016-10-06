package com.ttt.ai;

import org.encog.ml.CalculateScore;
import org.encog.ml.MLMethod;
import org.encog.neural.networks.BasicNetwork;

public class HalScore implements CalculateScore {

	@Override
	public double calculateScore(MLMethod method) {
		Hal pilot = new Hal(size,(BasicNetwork)method);
		return pilot.scorePilot();
	}

	@Override
	public boolean requireSingleThreaded() {
		return false;
	}

	@Override
	public boolean shouldMinimize() {
		return false;
	}

}
