package com.ttt.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public abstract class GeneticAlgorithm {

	private ArrayList<JNeuralNetwork> matingPool = new ArrayList<JNeuralNetwork>();
	private static Random random = new Random();

	public static JNeuralNetwork mutate(JNeuralNetwork nn, double mutateRate) {
		JNeuralNetwork mutated = new JNeuralNetwork(nn);
		mutated.makeWeightGroups();

		for (int i = 0; i < nn.getWeightGroups().size(); i++) {
			JWeightGroup wg = mutated.getWeightGroups().get(i);
			for (int d = 0; d < wg.getWeights().length; d++) {
				if (random.nextDouble() < mutateRate) {
					wg.setWeight(d, (random.nextDouble() - 0.5));
				} else {
					wg.setWeight(d, nn.getWeightGroups().get(i).getWeights()[d]);
				}
			}
			mutated.setWeightGroup(i, wg);
		}
		if (random.nextDouble() < mutateRate) {
			if (random.nextDouble() < mutateRate * 10) { // Add or remove Layer
															// to
				// NeuralNetwork
				JNeuralNetwork changedDesign = new JNeuralNetwork(nn);
				int index;
				if (nn.getLayers().size() <= 2) {
					index = 1;
				} else {
					index = random.nextInt(nn.getLayers().size() - 2) + 1;
				}
				if (random.nextBoolean() || nn.getLayers().size() == 2) { // Add
																			// a
																			// layer
					int neurons = nn.getTotalNeurons() / nn.getLayers().size();
					changedDesign.addLayerAt(index, new JLayer(neurons));
				} else { // Remove a layer
					changedDesign.removeLayer(index);
				}
				changedDesign.makeWeightGroups();
				return changedDesign;
			} else { // Add or remove a neuron from a layer
				JNeuralNetwork changedDesign = new JNeuralNetwork(nn);
				if (nn.getLayers().size() > 2) {

					int changeLayer = random.nextInt(nn.getLayers().size() - 2) + 1;

					JLayer l = nn.getLayers().get(changeLayer);
					int deltaNeurons = pickMutationLevel();
					if (random.nextBoolean()) {
						for (int i = 0; i < deltaNeurons; i++) {
							l.addNeuron(new JNeuron());
						}
					} else {
						l = new JLayer();
						for (int i = 0; i < Math.max(1,
								nn.getLayers().get(changeLayer).getNeurons().length - deltaNeurons); i++) {
							l.addNeuron(nn.getLayers().get(changeLayer).getNeuron(i));
						}
					}

					changedDesign.setLayer(changeLayer, l);
					changedDesign.makeWeightGroups();

					for (int w = 0; w < nn.getWeightGroups().size(); w++) {
						JWeightGroup wg = nn.getWeightGroups().get(w);
						JWeightGroup newWg = changedDesign.getWeightGroups().get(w);
						int index = 0;
						while (index < wg.getWeights().length && index < newWg.getWeights().length) {
							newWg.setWeight(index, wg.getWeight(index));
							index++;
						}
						changedDesign.setWeightGroup(w, newWg);
					}
					return changedDesign;
				}
			}
		}

		return mutated;
	}

	private static int pickMutationLevel() {
		int[] bin = { 1, 1, 1, 1, 1, 1, 2, 2, 2, 3 }; // 1 60%, 2 30%, 3 10%
		return bin[random.nextInt(bin.length)];
	}

	public static JNeuralNetwork crossover(JNeuralNetwork nn, JNeuralNetwork nn2) {
		JNeuralNetwork crossed;
		JNeuralNetwork copied;
		if (random.nextBoolean()) {
			crossed = new JNeuralNetwork(nn);
			copied = nn;
		} else {
			crossed = new JNeuralNetwork(nn2);
			copied = nn2;
		}

		crossed.makeWeightGroups();
		crossed.setWeightGroups(copied.getWeightGroups());

		int i = 0;
		while (i < nn.getWeightGroups().size() && i < nn2.getWeightGroups().size()) {
			JWeightGroup wg = nn.getWeightGroups().get(i);
			JWeightGroup wg2 = nn2.getWeightGroups().get(i);
			JWeightGroup newWg = crossed.getWeightGroups().get(i);

			if (wg.getWeights().length == newWg.getWeights().length) {
				newWg.setWeights(wg.getWeights());
			} else {
				newWg.setWeights(wg2.getWeights());
			}

			int index = 0;
			while (index < wg.getWeights().length && index < wg2.getWeights().length) {
				if (random.nextBoolean()) {
					newWg.setWeight(index, wg.getWeight(index));
				} else {
					newWg.setWeight(index, wg2.getWeight(index));
				}
				index++;
			}
			crossed.setWeightGroup(i, newWg);
			i++;
		}
		return crossed;
	}

	public static JNeuralNetwork acceptReject(ArrayList<Individual> pool, int maxFitness) {
		Random random = new Random();
		int besafe = 0;
		while (besafe < 10000) {
			int index = random.nextInt(pool.size());
			Individual partner = pool.get(index);
			int r = random.nextInt(maxFitness);
			double key = partner.fitness;

			if (r < key) {
				return partner.nn;
			}
			besafe++;
		}
		return pool.get(0).nn;

	}

	public JNeuralNetwork pickParent(JNeuralNetwork not, int iteration) {
		// System.out.println(matingPool.size());
		JNeuralNetwork parent = matingPool.get(random.nextInt(matingPool.size()));
		if (iteration > 100) {
			return parent;
		} else if (parent == not) {
			return pickParent(not, iteration + 1);
		} else {
			return parent;
		}
	}

	public void populateMatingPool(ArrayList<Individual> pool) {
		matingPool.clear();
		double lowest = Double.MAX_VALUE; // So lowest fitness = 0
		for (Individual hash : pool) {
			double fitness = hash.fitness;
			if (fitness < lowest) {
				lowest = fitness;
			}
		}

		for (Individual hash : pool) {
			double fitness = hash.fitness;
			for (int i = 0; i < fitness + Math.abs(lowest) + 1; i++) {
				matingPool.add(hash.nn);
			}
		}
	}

	public static ArrayList<Individual> getHighestHalf(ArrayList<Individual> pool) {
		Collections.sort(pool);
		ArrayList<Individual> newPool = new ArrayList<Individual>();
		for (int i = 0; i < pool.size() / 2; i++) {
			newPool.add(pool.get(i));
		}
		return newPool;
	}
	
	public static ArrayList<Individual> getSorted(ArrayList<Individual> pool) {
		Collections.sort(pool);
		return pool;
	}

	public ArrayList<JNeuralNetwork> getMatingPool() {
		return matingPool;
	}

	public abstract double selection(JNeuralNetwork nn);

}
