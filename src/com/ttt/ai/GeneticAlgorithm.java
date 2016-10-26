package com.ttt.ai;

import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {

	public static void main(String[] args) {

		JNeuralNetwork base = new JNeuralNetwork();

		base.addLayer(new JLayer(9));
		base.addLayer(new JLayer(15));
		base.addLayer(new JLayer(9));

		base.makeWeightGroups();

		JLayer l = new JLayer(9);
		l.setNeuron(0, new JNeuron(1));
		l.setNeuron(1, new JNeuron(0));
		l.setNeuron(2, new JNeuron(0));
		l.setNeuron(3, new JNeuron(-2));
		l.setNeuron(4, new JNeuron(4));
		l.setNeuron(5, new JNeuron(1));
		l.setNeuron(6, new JNeuron(0));
		l.setNeuron(7, new JNeuron(2));
		l.setNeuron(8, new JNeuron(-1));

		base.setInputs(l);

		base.flush();

		System.out.println("Highest base output: " + base.getOutputs().getHighest().getInput());
		
		JNeuralNetwork mutated = mutate(base, 0.1);
		mutated.flush();
		System.out.println("Highest mutated output: " + mutated.getOutputs().getHighest().getInput());
		

	}

	public static JNeuralNetwork mutate(JNeuralNetwork jnn, double mutateRate) {
		Random r = new Random();

		JNeuralNetwork mutated = new JNeuralNetwork();
		for(JLayer l : jnn.getLayers()){
			mutated.addLayer(l);
		}
		
		ArrayList<JWeightGroup> weights = new ArrayList<>();
		for (JWeightGroup wg : jnn.getWeightGroups()) {
			for(int i = 0; i < wg.getWeights().length; i++){
				if(r.nextDouble() < mutateRate){
					wg.setWeight(i, r.nextDouble());
				}
			}
			weights.add(wg);
		}
		mutated.setWeightGroups(weights);
		return mutated;
	}

}
