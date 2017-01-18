package com.ttt.ai.hal;

import java.util.HashMap;

import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;

public class Hal {
	private BasicNetwork network;
	private int size;
	private HashMap<Integer, int[]> map = new HashMap<>();

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
			sim.minimaxMove(1);
			
			MLData input = new BasicMLData((size * size));
			int times = 0;
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					input.add(times, sim.getBoard()[i][j]);
					times++;
				}
			}
			if (sim.testForWin()) {
				break gameloop;
			}
			MLData output = this.network.compute(input);
			double value[] = output.getData();
			while (true) {
				int maxIndex = 0;
				double max = value[0];
				for (int i = 0; i < value.length; i++) {
					if (value[i] >= max) {
						max = value[i];
						maxIndex = i;
					}
				}
				if (sim.place(-1, map.get(maxIndex)[0], map.get(maxIndex)[1])) {
					break;
				}
				value[maxIndex] = -10;
			}
			sim.printBoard();

			if (sim.testForWin()) {
				break gameloop;
			}
			sim.testForWin();
		}
		if (sim.isTie()) {
			System.out.println("tie");
			return 0;
		} else if (sim.isWinner(1)) {
			System.out.println("winner");
			return 0.2;
		} else {
			System.out.println("loser");
			return -0.2;
		}
//		return (sim.score(1));
	}
}
