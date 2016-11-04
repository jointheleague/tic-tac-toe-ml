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
				if(random.nextDouble() < mutateRate){
					wg.setWeight(d, (random.nextDouble() - 0.5));
				} else{
					wg.setWeight(d, nn.getWeightGroups().get(i).getWeights()[d]);
				}
			}
			mutated.setWeightGroup(i, wg);
		}
		return mutated;
	}

	public static JNeuralNetwork crossover(JNeuralNetwork nn, JNeuralNetwork nn2) {
		JNeuralNetwork crossed = new JNeuralNetwork(nn);
		crossed.makeWeightGroups();
		
		for (int i = 0; i < nn.getWeightGroups().size(); i++) {
			JWeightGroup wg = crossed.getWeightGroups().get(i);
			int crossPoint = random.nextInt(wg.getWeights().length);
			for (int d = 0; d < wg.getWeights().length; d++) {
				if(d > crossPoint){
					wg.setWeight(d, nn.getWeightGroups().get(i).getWeights()[d]);
				} else{
					wg.setWeight(d, nn2.getWeightGroups().get(i).getWeights()[d]);
				}
			}
			crossed.setWeightGroup(i, wg);
		}
		return crossed;
	}

	public JNeuralNetwork acceptReject(ArrayList<Individual> pool, int maxFitness) {
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
		//System.out.println(matingPool.size());
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
		double lowest = Double.MAX_VALUE; //So lowest fitness = 0
		for (Individual hash : pool) {
			double fitness = hash.fitness;
			if(fitness < lowest){
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

	public ArrayList<Individual> getHighestHalf(ArrayList<Individual> pool) {
//		ArrayList<HashMap<Double, NeuralNetwork>> newPool = new ArrayList<HashMap<Double, NeuralNetwork>>();
//		int size = pool.size()/2;
//		for (int i = 0; i < size; i++) {
//			double highest = Double.MIN_VALUE;
//			NeuralNetwork best = null;
//			for (HashMap<Double, NeuralNetwork> hash : pool) {
//				double fitness = (double) hash.keySet().toArray()[0];
//				if (fitness > highest) {
//					highest = fitness;
//					best = hash.get(fitness);
//				}
//			}
//			HashMap<Double, NeuralNetwork> hash1 = new HashMap<Double, NeuralNetwork>();
//			hash1.put(highest, best);
//			newPool.add(hash1);
//			for (int a = 0; a < pool.size(); a++) {
//				HashMap<Double, NeuralNetwork> hash = pool.get(a);
//				double fitness = (double) hash.keySet().toArray()[0];
//				if (fitness == fitness) {
//					pool.remove(hash);
//					break;
//				}
//			}
//		}
//		return newPool;

		Collections.sort(pool);
		ArrayList<Individual> newPool = new ArrayList<Individual>();
		for(int i = 0; i < pool.size()/2; i++){
			newPool.add(pool.get(i));
		}
		return newPool;
	}

	public ArrayList<JNeuralNetwork> getMatingPool() {
		return matingPool;
	}

	public abstract double selection(JNeuralNetwork nn);

}
