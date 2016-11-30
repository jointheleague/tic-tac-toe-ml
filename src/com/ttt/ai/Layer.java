package com.ttt.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class Layer implements Comparable<Layer>, Iterable<Neuron> {
	private ArrayList<Neuron> neurons = new ArrayList<>();
	private int depth;
	private NeuralNetwork network;

	public Layer() {
	}

	public Layer(Neuron[] ns) {
		for (Neuron n : ns) {
			addNeuron(n);
		}
	}

	public void addNeuron(Neuron n) {
		n.setLayer(this);
		neurons.add(n);
		n.setIndexInLayer(neurons.size() - 1);
	}

	public void removeNeuron(Neuron n) {
		neurons.remove(n);
	}

	public void setNeurons(Neuron[] n) {
		this.neurons = new ArrayList<>(Arrays.asList(n));
	}

	public Neuron[] getNeurons() {
		return this.neurons.toArray(new Neuron[neurons.size()]);
	}

	public int getDepth() {
		return depth;
	}

	void setDepth(int depth) {
		this.depth = depth;
	}

	public boolean isInput() {
		return depth == 0;
	}

	public boolean isLogic() {
		return !isInput() && !isOutput();
	}

	public boolean isOutput() {
		return depth == network.getOutputLayer().getDepth();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Layer) {
			Layer l = (Layer) o;
			return l.depth == this.depth && l.neurons.equals(this.neurons);
		}
		return false;
	}

	@Override
	public int compareTo(Layer o) {
		return depth - o.depth;
	}

	public int size() {
		return neurons.size();
	}

	public Neuron getNeuron(int index) {
		return neurons.get(index);
	}

	public void setNeuron(int index, Neuron neuron) {
		neurons.set(index, neuron);
	}

	private static final Random RANDOM = new Random();

	public void connectSynapses(Layer layer) {
		for (Neuron neuron : this) {
			for (Neuron other : layer) {
				neuron.addOutgoingSynapse(
						new Synapse(neuron, other, RANDOM.nextDouble() * (RANDOM.nextInt(2) == 0 ? -1 : 1)));
			}
		}
	}

	@Override
	public int hashCode() {
		return 31 + neurons.hashCode();
	}

	@Override
	public Iterator<Neuron> iterator() {
		return neurons.iterator();
	}

	@Override
	public String toString() {
		String str = "[";
		for (Neuron n : this) {
			str += n.toString() + ", ";
		}
		return str.substring(0, str.length() - 2) + "]";
	}

	public int indexOf(Neuron n) {
		return neurons.indexOf(n);
	}

	public NeuralNetwork getNetwork() {
		return network;
	}

	public void setNetwork(NeuralNetwork network) {
		this.network = network;
	}
}
