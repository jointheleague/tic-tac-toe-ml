package com.ttt.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Layer implements Comparable<Layer>, Iterable<Neuron> {
	private ArrayList<Neuron> neurons = new ArrayList<>();
	private int depth;

	public Layer() {
	}

	public Layer(int neurons) {
		for (int i = 0; i < neurons; i++) {
			addNeuron(new Neuron(0));
		}
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
		return neurons.toArray(new Neuron[neurons.size()]);
	}

	public int getDepth() {
		return depth;
	}

	void setDepth(int depth) {
		this.depth = depth;
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
	
	public void setNeuron(int index, double value) {
		Neuron n = neurons.get(index);
		n.setValue(value);
		neurons.set(index, n);
	}

	public void connectSynapses(Layer layer) {
		Random random = new Random();

		for (Neuron neuron : this) {
			for (Neuron other : layer) {
				neuron.addOutgoingSynapse(new Synapse(neuron, other, random.nextFloat() - 0.5f));
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

	public Neuron getHighest() {
		Neuron highest = new Neuron(Double.MIN_VALUE);
		for (Neuron n : neurons) {
			if (n.getValue() > n.getValue()) {
				highest = n;
			}
		}
		return highest;
	}

	public Neuron getLowest() {
		Neuron lowest = new Neuron(Double.MAX_VALUE);
		for (Neuron n : neurons) {
			if (n.getValue() < n.getValue()) {
				lowest = n;
			}
		}
		return lowest;
	}

	public ArrayList<Integer> getHighest2Lowest() {
		ArrayList<Neuron> copy = new ArrayList<Neuron>();
		copy.addAll(neurons);
		Collections.sort(copy);

		ArrayList<Integer> sorted = new ArrayList<Integer>();
		for(Neuron n : copy){
			sorted.add(neurons.indexOf(n));
		}
		
		return sorted;
	}
}
