package com.ttt.ai;

public class GeneticAlgorithm {

	public static void main(String[] args) {
		NeuralNetwork base = new NeuralNetwork();
		
		base.setInputLayer(new Layer(new Neuron[] { new Neuron(0), new Neuron(0), new Neuron(0), new Neuron(0), new Neuron(0), new Neuron(0), new Neuron(0), new Neuron(0), new Neuron(0)}));
		base.addLogicLayer(new Layer(new Neuron[] { new Neuron(0), new Neuron(0), new Neuron(0), new Neuron(0), new Neuron(0), new Neuron(0)}));
		base.setOutputLayer(new Layer(new Neuron[] { new Neuron(0), new Neuron(0), new Neuron(0), new Neuron(0), new Neuron(0), new Neuron(0), new Neuron(0), new Neuron(0), new Neuron(0)}));

		base.connectSynapsesBetweenLayers();
		
		Population p = new Population(base, 200, 0.01);
		double highestFit = p.draw();
		while(highestFit < 144){
			p.draw();
		}
		
	}

}
