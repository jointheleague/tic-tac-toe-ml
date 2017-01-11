package com.ttt.ai.hal;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.encog.engine.network.activation.ActivationTANH;
import org.encog.ml.MLMethod;
import org.encog.ml.MethodFactory;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.genetic.MLMethodGeneticAlgorithm;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.pattern.FeedForwardPattern;

import com.ttt.model.Tile;
import com.ttt.model.TilePosition;

public class HALMainController {
	static int tb[][] = { { 0, 0, 1, 0, 0 }, { 0, 0, 1, 0, 0 }, { 0, 0, 1, 0, 0 }, { 0, 0, 1, 0, 0 },
			{ 0, 0, 1, 0, 0 }, };

	static int size = 3;

	public static BasicNetwork createNetwork() {
		FeedForwardPattern pattern = new FeedForwardPattern();
		pattern.setInputNeurons(size * size);
		pattern.addHiddenLayer(size * size);
		pattern.addHiddenLayer(size * size);
		pattern.addHiddenLayer(size * size);
		pattern.addHiddenLayer(size * size);
		pattern.setOutputNeurons(size * size);
		pattern.setActivationFunction(new ActivationTANH());
		BasicNetwork network = (BasicNetwork) pattern.generate();
		network.reset();
		return network;
	}

	private static final BasicNetwork result = createNetwork();

	public static void learn() {
		MLMethodGeneticAlgorithm train = new MLMethodGeneticAlgorithm(new MethodFactory() {
			@Override
			public MLMethod factor() {
				result.reset();
				return result;
			}
		}, new HalScore(), 1);

		JFrame frame = new JFrame();
		JPanel panel = new JPanel();

		JProgressBar bar = new JProgressBar();
		bar.setIndeterminate(false);
		bar.setStringPainted(true);
		bar.setString("0%");
		bar.setValue(0);
		panel.add(bar);

		frame.add(panel);
		frame.setVisible(true);
		frame.pack();

		int times = 100000;
		for (int i = 0; i < times; i++) {
			train.iteration();
			if (i % (times / 100) == 0) {
				bar.setValue(i / (times / 100));
				bar.setString(bar.getValue() + "%");
			}
		}
		train.finishTraining();
		frame.dispose();
	}

	public static TilePosition getNextPosition(Tile[][] tiles) {
		MLData data = new BasicMLData(size * size);

		int val = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				data.add(val++, tiles[i][j] == Tile.X ? 1 : (tiles[i][j] == Tile.O ? -1 : 0));
			}
		}
		double[] value = HALMainController.result.compute(data).getData();
		while (true) {
			int maxIndex = 0;
			double max = value[0];
			for (int i = 0; i < value.length; i++) {
				if (value[i] >= max) {
					max = value[i];
					maxIndex = i;
				}
			}
			int x = maxIndex % 3;
			int y = maxIndex / 3;
			if (tiles[x][y] == Tile.EMPTY) {
				return new TilePosition(x, y);
			}
			value[maxIndex] = -10;
		}
	}
}