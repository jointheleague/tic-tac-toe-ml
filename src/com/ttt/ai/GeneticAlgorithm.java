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

	public NeuralNetwork pickParent(NeuralNetwork not) {
		NeuralNetwork parent = matingPool.get(random.nextInt(matingPool.size()));
		if(parent == not){
			return pickParent(not);
		} else{
			return parent;
		} 
	}
	
	public void populateMatingPool(ArrayList<HashMap<Double, NeuralNetwork>> pool) {
		for (HashMap<Double, NeuralNetwork> hash : pool) {
			double fitness = (double) hash.keySet().toArray()[0];
			for(int i = 0; i < fitness; i++){
				matingPool.add(hash.get(fitness));
			}
		}
	}
	
	public abstract double selection(NeuralNetwork nn);

}
