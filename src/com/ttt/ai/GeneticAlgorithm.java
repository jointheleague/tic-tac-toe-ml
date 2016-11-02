package com.ttt.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class GeneticAlgorithm {

	private ArrayList<NeuralNetwork> matingPool = new ArrayList<NeuralNetwork>();
	private Random random = new Random();

	public NeuralNetwork mutate(NeuralNetwork nn, double mutateRate) {
		Random r = new Random();

		for (int i = 0; i < nn.getAllLayers().length - 1; i++) {
			Layer l = nn.getAllLayers()[i];
			for (Neuron n : l.getNeurons()) {
				for (Synapse s : n.getOutgoingSynapses()) {
					if (r.nextDouble() < mutateRate) {
						s.setWeight(r.nextDouble());
					}
				}
			}
		}
		return nn;
	}

	public NeuralNetwork crossover(NeuralNetwork nn, NeuralNetwork nn2) {
		Random r = new Random();

		for (int i = 0; i < nn.getAllLayers().length - 1; i++) {
			Layer l = nn.getAllLayers()[i];
			int crossPoint = r.nextInt(l.getNeurons().length);
			for (int a = 0; a < l.getNeurons().length; a++) {
				if (a > crossPoint) {
					Neuron n = l.getNeuron(a);
					for (int d = 0; d < n.getOutgoingSynapses().length; d++) {
						Synapse s = n.getOutgoingSynapses()[d];
						s.setWeight(nn2.getAllLayers()[i].getNeuron(a).getOutgoingSynapses()[d].getWeight());
					}
				}
			}
		}
		return nn;
	}

	public NeuralNetwork acceptReject(ArrayList<HashMap<Double, NeuralNetwork>> pool, int maxFitness) {
		Random random = new Random();
		int besafe = 0;
		while (besafe < 10000) {
			int index = random.nextInt(pool.size());
			HashMap<Double, NeuralNetwork> partner = pool.get(index);
			int r = random.nextInt(maxFitness);
			double key = partner.keySet().toArray(new Double[partner.keySet().size()])[0];

			if (r < key) {
				return partner.get(key);
			}
			besafe++;
		}
		return pool.get(0).get(pool.get(0).keySet().toArray(new Double[1])[0]);

	}

	public NeuralNetwork pickParent(NeuralNetwork not, int iteration) {
		NeuralNetwork parent = matingPool.get(random.nextInt(matingPool.size()));
		if (iteration > 100) {
			return (matingPool.indexOf(not) == 0) ? matingPool.get(matingPool.size() - 1)
					: matingPool.get(matingPool.indexOf(not) - 1);
		} else if (parent == not) {
			return pickParent(not, iteration + 1);
		} else {
			return parent;
		}
	}

	public void populateMatingPool(ArrayList<HashMap<Double, NeuralNetwork>> pool) {
		matingPool.clear();
		for (HashMap<Double, NeuralNetwork> hash : pool) {
			double fitness = (double) hash.keySet().toArray()[0];
			for (int i = 0; i < fitness; i++) {
				matingPool.add(hash.get(fitness));
			}
		}
	}

	public ArrayList<HashMap<Double, NeuralNetwork>> getHighestHalf(ArrayList<HashMap<Double, NeuralNetwork>> pool) {
		ArrayList<HashMap<Double, NeuralNetwork>> newPool = new ArrayList<HashMap<Double, NeuralNetwork>>(pool.size()/2);
		for (int i = 0; i < pool.size() / 2; i++) {
			double highest = Double.MIN_VALUE;
			NeuralNetwork best = null;
			for (HashMap<Double, NeuralNetwork> hash : pool) {
				double fitness = (double) hash.keySet().toArray()[0];
				if (fitness > highest) {
					highest = fitness;
					best = hash.get(fitness);
				}
			}
			HashMap<Double, NeuralNetwork> hash1 = new HashMap<Double, NeuralNetwork>();
			hash1.put(highest, best);
			newPool.add(hash1);
			for (int a = 0; a < pool.size(); a++) {
				HashMap<Double, NeuralNetwork> hash = pool.get(a);
				double fitness = (double) hash.keySet().toArray()[0];
				if (fitness == fitness) {
					pool.remove(hash);
					break;
				}
			}
			
		}
		return newPool;
	}

	public ArrayList<NeuralNetwork> getMatingPool() {
		return matingPool;
	}

	public abstract double selection(NeuralNetwork nn);

}
