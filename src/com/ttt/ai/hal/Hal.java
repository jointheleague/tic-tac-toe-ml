package com.ttt.ai.hal;

import java.util.HashMap;

import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;

public class Hal {

	private BasicNetwork network;
	int size;
	HashMap<Integer, int[]> map = new HashMap<>();

	public Hal(int size, BasicNetwork network) {
		this.network = network;
		this.size = size;
		int key = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int l[] = { i, j };
				map.put(key, l);
				key++;
			}
		}
	}

	public double scorePilot() {
		TTTSim sim = new TTTSim(size);
		gameloop: while (sim.playing()) {
			MLData input = new BasicMLData((size * size));
			int times = 0;
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					input.add(times, sim.getBoard()[i][j]);
					times++;
				}
			}
			MLData output = this.network.compute(input);
			double value[] = output.getData();
			boolean findWorkingLocaiton = true;
			while (findWorkingLocaiton) {
				int maxIndex = 0;
				double max = value[0];
				for (int i = 0; i < value.length; i++) {
					if (value[i] >= max) {
						max = value[i];
						maxIndex = i;
					}
				}
				findWorkingLocaiton = !sim.place(1, map.get(maxIndex)[0], map.get(maxIndex)[1]);
				value[maxIndex] = -10;
			}

			sim.printBoard();
			if (sim.TestForWin()) {
				break gameloop;
			}
			sim.randomMove(-1);
			sim.printBoard();
			sim.TestForWin();
		}
		return (sim.score(1));
	}
}
