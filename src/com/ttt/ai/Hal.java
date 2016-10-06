package com.ttt.ai;

import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;

public class Hal {
	private BasicNetwork network;
	int size;

	public Hal(int size, BasicNetwork network) {
		this.network = network;
		this.size = size;
	}

	public int scorePilot() {
		TTTSim sim = new TTTSim(size);
		while (sim.playing()) {
			MLData input = new BasicMLData(size ^ 2);
			int times = 0;
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					input.add(times, sim.getBoard()[i][j]);
					times++;
				}
			}
			MLData output = this.network.compute(input);
			double value[] = output.getData();
			for (int i = 0; i < value.length; i++) {
				System.out.println(value[i]);
			}
		}
		return (sim.score(-1));
	}
}
