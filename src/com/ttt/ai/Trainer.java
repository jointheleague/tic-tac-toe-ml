package com.ttt.ai;

public class Trainer {

	public static void main(String[] args) {
		NeuralNetwork nbase = new NeuralNetwork();
		nbase.setInputLayer(new Layer(new Neuron[] { new Neuron(0), new Neuron(0), new Neuron(0), new Neuron(0),
				new Neuron(0), new Neuron(0), new Neuron(0), new Neuron(0), new Neuron(0), new Neuron(1) }));
		nbase.addLogicLayer(new Layer(10));
		nbase.addLogicLayer(new Layer(10));
		nbase.setOutputLayer(new Layer(9));

		nbase.connectSynapsesBetweenLayers();
		nbase.compute();
		
		Population pop = new Population(nbase, 100, 0.01, 3);

		pop.setDebug(true);
		for(int i = 0; i < 100; i++){
			pop.runGeneration();
			System.out.println(pop.getOutput());
		}

	}

}
