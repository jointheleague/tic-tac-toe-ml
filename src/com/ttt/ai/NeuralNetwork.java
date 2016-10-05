package com.ttt.ai;

import java.util.ArrayList;

public class NeuralNetwork {

	ArrayList<Layer> layers;
	ArrayList<WeightGroup> weights;

	public static void main(String[] args) {
		NeuralNetwork nn = new NeuralNetwork();
		nn.addLayer(new Layer(9));
		nn.addLayer(new Layer(9));
		nn.addLayer(new Layer(5));
		nn.addLayer(new Layer(2));
		nn.makeWeightGroups();

		Layer input = new Layer(9);
		input.setNeuron(0, new Neuron(1));
		input.setNeuron(1, new Neuron(1));
		input.setNeuron(2, new Neuron(0));
		input.setNeuron(3, new Neuron(-1));
		input.setNeuron(4, new Neuron(0));
		input.setNeuron(5, new Neuron(-1));
		input.setNeuron(6, new Neuron(0));
		input.setNeuron(7, new Neuron(0));
		input.setNeuron(8, new Neuron(0));
		nn.setInputs(input);
		nn.flush();

		// not using sigmoid activation
		System.out.print("[");
		for (Neuron n : nn.getOutputs().getNeurons()) {
			System.out.print(n.getInput() + ", ");
		}
		System.out.println("]");
		// System.out.println(nn.getOutputs().getNeurons()[0].getInput());
	}

	public NeuralNetwork() {
		layers = new ArrayList<Layer>();
		weights = new ArrayList<WeightGroup>();
	}

	public void addLayer(Layer layer) {
		layers.add(layer);
	}

	public void makeWeightGroups() {
		if (layers.size() < 2) {
			try {
				throw new Exception("Cannot create weight groups for 1 layer.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			for (int i = 0; i < layers.size() - 1; i++) {
				weights.add(WeightGroup.connectLayers(layers.get(i), layers.get(i + 1)));
			}
		}
	}

	public void setInputs(Layer l) {
		Layer first = layers.get(0);
		if (l.size() != first.size()) {
			throw new ArrayIndexOutOfBoundsException("The number of inputs and first " + "layer neurons don't match");
		} else {
			for (int i = 0; i < l.size(); i++) {
				first.setNeuron(i, l.getNeuron(i));
			}
			layers.set(0, first);
		}
	}

	public void flush() {
		// iterate through the layers (not the last one)
		for (int i = 0; i < layers.size() - 1; i++) {
			Layer current = layers.get(i);
			Layer next = layers.get(i + 1);
			// iterate through the Neurons in the next layer
			for (int neuronNum = 0; neuronNum < next.size(); neuronNum++) {
				// iterate through the weights associated with that Neuron
				for (int weight = neuronNum * current.size(); weight < (neuronNum * current.size())
						+ current.size(); weight++) {
					// current value of the Neuron
					double neuronCur = next.getNeuron(neuronNum).getOutput();
					// the output of Neuron in the current layer associated with
					// the current weight
					double mult = current.getNeuron(weight % current.size()).getOutput();
					if (i == 0) { // input layer (no sigmoid activation)
						neuronCur = next.getNeuron(neuronNum).getInput();
						mult = current.getNeuron(weight % current.size()).getInput();
					}
					//sets the Neuron's input to the existing input + the new input
					next.setNeuron(neuronNum, new Neuron(neuronCur + (weights.get(i).getWeights()[weight] * mult)));
				}
			}
		}
	}

	public Layer getOutputs() {
		return layers.get(layers.size() - 1);
	}

}
