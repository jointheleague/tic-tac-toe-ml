package com.ttt.test;

import com.ttt.ai.Layer;
import com.ttt.ai.NeuralNetwork;
import com.ttt.ai.Neuron;
import com.ttt.ai.Synapse;

public class NeuralNetworkPOC {
	private static int runs = 1000;

	// This program was used as a Proof Of Concept for the use of Neural Networks to gradually learn towards certain goals.
	// 1000 test cases are run, each with 1000 times to attempt to output a number close to 1. 
	// In each test case, the Neural Network is first randomized, then edited to weigh Synapses to act more successfully.
	
	public static void main(String[] args) {
		int bad = 0;

		for (int i = 0; i < runs; i++) {
			double failed = test(createNewNetwork());
			double failedPercent = failed / runs;

			System.out.println("Test #" + (i + 1) + " failed " + (failedPercent * 100) + "% of the time.");
			if (failedPercent * 100 >= 7.5) {
				bad++;
			}
		}
		System.out.println((((double) bad / runs) * 100) + "% of tests failed the margin.");
	}

	private static NeuralNetwork createNewNetwork() {
		NeuralNetwork net = new NeuralNetwork();
		net.setInputLayer(new Layer(new Neuron[] { new Neuron(-1), new Neuron(0), new Neuron(1) }));
		net.addLogicLayer(new Layer(new Neuron[] { new Neuron(0) }));
		net.setOutputLayer(new Layer(new Neuron[] { new Neuron(0) }));
		net.connectSynapsesBetweenLayers();
		return net;
	}

	private static double test(NeuralNetwork net) {
		int fail = 0;
		for (int i = 0; i < runs; i++) {
			net.compute();

			Neuron out = net.getOutputLayer().getNeuron(0);
			if (out.getValue() < 0.9D || out.getValue() > 1.1D) {
				for (Neuron input : net.getInputLayer()) {
					for (Synapse synOut : input.getOutgoingSynapses()) {
						if (out.getValue() < 0.9D) {
							synOut.increaseWeight(0.01);
						} else {
							synOut.decreaseWeight(0.01);
						}
					}
				}

				fail++;
			}
		}
		return fail;
	}
}
