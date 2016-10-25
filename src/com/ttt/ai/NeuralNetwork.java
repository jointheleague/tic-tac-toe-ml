package com.ttt.ai;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class NeuralNetwork implements Iterable<Layer> {
	public static final int INPUT_LAYER_DEPTH = 0;

	private Layer inputLayer;
	private ArrayList<Layer> logicLayers = new ArrayList<>();
	private Layer outputLayer;

	public NeuralNetwork() {
		setInputLayer(new Layer());
		setOutputLayer(new Layer());
	}

	public Layer getInputLayer() {
		return inputLayer;
	}

	public void setInputLayer(Layer inputLayer) {
		this.inputLayer = inputLayer;
		inputLayer.setDepth(INPUT_LAYER_DEPTH);
	}

	public Layer getOutputLayer() {
		return outputLayer;
	}

	public void setOutputLayer(Layer outputLayer) {
		this.outputLayer = outputLayer;
		outputLayer.setDepth(logicLayers.size() > 0 ? logicLayers.get(logicLayers.size() - 1).getDepth() + 1 : 1);
	}

	public void addLogicLayer(Layer l) {
		logicLayers.add(l);
		l.setDepth(logicLayers.size());
		Collections.sort(logicLayers);
	}

	public void removeLogicLayer(Layer l) {
		logicLayers.remove(l);
		Collections.sort(logicLayers);
	}

	public Layer[] getLogicLayers() {
		return logicLayers.toArray(new Layer[logicLayers.size()]);
	}

	public void connectSynapsesBetweenLayers() {
		ArrayList<Layer> layers = getAllLayersList();
		for (int i = 0; i < layers.size() - 1; i++) {
			Layer current = layers.get(i);
			Layer next = layers.get(i + 1);

			current.connectSynapses(next);
		}
	}

	public void compute() {
		ArrayList<Layer> layers = getAllLayersList();

		for (int i = 0; i < layers.size() - 1; i++) {
			Layer current = layers.get(i);
			Layer next = layers.get(i + 1);

			for (Neuron firingNeuron : current) {
				for (Synapse synapse : firingNeuron.getOutgoingSynapses()) {
					double mult = synapse.getTargetNeuron().getOutput();

					Neuron temp = synapse.getTargetNeuron();
					Neuron endpoint = synapse.getTargetNeuron();
					endpoint.setValue(firingNeuron.getValue() + (synapse.getWeight() * mult));
					synapse.setTargetNeuron(endpoint);
					next.setNeuron(next.indexOf(temp), endpoint);
				}
			}
		}
	}

	private ArrayList<Layer> getAllLayersList() {
		ArrayList<Layer> layers = new ArrayList<>();
		layers.add(inputLayer);
		layers.addAll(logicLayers);
		layers.add(outputLayer);
		return layers;
	}

	public Layer[] getAllLayers() {
		ArrayList<Layer> l = getAllLayersList();
		return l.toArray(new Layer[l.size()]);
	}

	@Override
	public String toString() {
		String str = "";
		for (Layer l : getAllLayers()) {
			str += l.toString() + "\n";
		}
		return str.trim();
	}

	@Override
	public Iterator<Layer> iterator() {
		return getAllLayersList().iterator();
	}
}
