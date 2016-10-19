package org.ai;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class Layer implements Comparable<Layer>, Iterable<Neuron> {
	private ArrayList<Neuron> neurons = new ArrayList<>();
	private int depth;

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

	public void getNeurons(Neuron[] n) {
		this.neurons = new ArrayList<>(Arrays.asList(n));
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
}
