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

		System.out.println("Highest jnn base output: " + base.getOutputs().getHighest().getInput());
		
		JNeuralNetwork mutated = jmutate(base, 0.1);
		mutated.flush();
		System.out.println("Highest jnn mutated output: " + mutated.getOutputs().getHighest().getInput());
		
		NeuralNetwork nbase = new NeuralNetwork();
		nbase.setInputLayer(new Layer(new Neuron[] {new Neuron(1), new Neuron(0), new Neuron(0), new Neuron(-2), new Neuron(4), new Neuron(1), new Neuron(0), new Neuron(2), new Neuron(-1)}));
		nbase.addLogicLayer(new Layer(15));
		nbase.setOutputLayer(new Layer(9));
		
		nbase.connectSynapsesBetweenLayers();
		nbase.compute();
		
		NeuralNetwork nbase2 = new NeuralNetwork();
		nbase2.setInputLayer(new Layer(new Neuron[] {new Neuron(1), new Neuron(0), new Neuron(0), new Neuron(-2), new Neuron(4), new Neuron(1), new Neuron(0), new Neuron(2), new Neuron(-1)}));
		nbase2.addLogicLayer(new Layer(15));
		nbase2.setOutputLayer(new Layer(9));
		
		nbase2.connectSynapsesBetweenLayers();
		nbase2.compute();
		
		NeuralNetwork crossed = crossover(nbase, nbase2);
		crossed.compute();
		
		System.out.println("First nn base output: " + nbase.getOutputLayer().getHighest().getValue());
		
		NeuralNetwork nmutated = mutate(nbase, 0.1);
		nmutated.compute();

		System.out.println("First crossover base output: " + crossed.getOutputLayer().getHighest().getValue());
	}

	public static JNeuralNetwork jmutate(JNeuralNetwork jnn, double mutateRate) {
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
	
	public static NeuralNetwork mutate(NeuralNetwork nn, double mutateRate) {
		Random r = new Random();
		
		for (int i = 0; i < nn.getAllLayers().length - 1; i++) {
			Layer l = nn.getAllLayers()[i];
			for(Neuron n : l.getNeurons()){
				for(Synapse s : n.getOutgoingSynapses()){
					if(r.nextDouble() < mutateRate){
						s.setWeight(r.nextDouble());
					}
				}
			}
		}
		return nn;
	}
	
	public static NeuralNetwork crossover(NeuralNetwork nn, NeuralNetwork nn2) {
		Random r = new Random();
		
		for (int i = 0; i < nn.getAllLayers().length - 1; i++) {
			Layer l = nn.getAllLayers()[i];
			for(int a = 0; a < l.getNeurons().length; a++){
				Neuron n = l.getNeuron(a);
				for(int d = 0; d < n.getOutgoingSynapses().length; d++){
					Synapse s = n.getOutgoingSynapses()[d];
					if(r.nextBoolean()){
						s.setWeight(nn2.getAllLayers()[i].getNeuron(a).getOutgoingSynapses()[d].getWeight());
					}
				}
			}
		}
		return nn;
	}

}
