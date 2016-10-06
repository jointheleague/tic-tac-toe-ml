package com.ttt.ai;

import java.util.ArrayList;

public class NeuralNetwork {

	private ArrayList<Layer> layers;
	private ArrayList<WeightGroup> weights;

	/**
	 * {@code public ArrayList<Layer> getLayers()}
	 * @return The ArrayList of layers.
	 */
	public ArrayList<Layer> getLayers(){
		return layers;
	}
	
	/**
	 * {@code public ArrayList<WeightGroup> getWeightGroups()}
	 * @return The ArrayList of the WeightGroups.
	 */
	public ArrayList<WeightGroup> getWeightGroups(){
		return weights;
	}
	
	/**
	 * {@code public void setLayers(ArrayList<Layer> layers)}
	 * @param layers - the new ArrayList of Layers.
	 */
	public void setLayers(ArrayList<Layer> layers){
		this.layers = layers;
	}
	
	/**
	 * {@code public void setWeightGroups(ArrayList<WeightGroup> weights)}
	 * @param weights - the new ArrayList of WeightGroups.
	 */
	public void setWeightGroups(ArrayList<WeightGroup> weights){
		this.weights = weights;
	}
	
	public NeuralNetwork() {
		layers = new ArrayList<Layer>();
		weights = new ArrayList<WeightGroup>();
	}

	/**
	 * {@code public void addLayer(Layer layer)}
	 * @param layer - Layer to be added to the end of the neural network.
	 */
	public void addLayer(Layer layer) {
		layers.add(layer);
	}

	/**
	 * {@code public void makeWeightGroups()}
	 * <br><br>
	 * Makes WeightGroups based on the existing layers.
	 * @throws Exception Cannot create weight groups for only 1 layer.
	 */
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

	/**
	 * {@code public void setInputs(Layer l)}
	 * @param l - the Layer containing the inputs for the neural network.
	 * @throws ArrayIndexOutOfBoundsException Thrown when the number of inputs and first layer neurons do not match.
	 */
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

	/**
	 * {@code public void flush()}
	 * <br><br>
	 * Runs the given inputs through the neural network.
	 */
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
					// sets the Neuron's input to the existing input + the new
					// input
					next.setNeuron(neuronNum, new Neuron(neuronCur + (weights.get(i).getWeights()[weight] * mult)));
				}
			}
		}
	}

	/**
	 * {@code public Layer getOutputs()}
	 * @return The output layer.
	 */
	public Layer getOutputs() {
		return layers.get(layers.size() - 1);
	}

}
