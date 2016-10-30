package com.ttt.ai;
import java.util.ArrayList;

public class Neuron implements Comparable<Neuron>{
	private Layer layer;
	private double value;
	private int indexInLayer;
	private ArrayList<Synapse> incomingSynapses = new ArrayList<>();
	private ArrayList<Synapse> outgoingSynapses = new ArrayList<>();

	public Neuron(double value) {
		this.value = value;
	}
	
	public Neuron(){
		
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Layer getLayer() {
		return layer;
	}

	public void setLayer(Layer layer) {
		this.layer = layer;
	}

	public Synapse[] getIncomingSynapses() {
		return incomingSynapses.toArray(new Synapse[incomingSynapses.size()]);
	}

	public Synapse[] getOutgoingSynapses() {
		return outgoingSynapses.toArray(new Synapse[outgoingSynapses.size()]);
	}

	public void addIncomingSynapse(Synapse in) {
		this.incomingSynapses.add(in);
	}

	public void addOutgoingSynapse(Synapse out) {
		out.getTargetNeuron().addIncomingSynapse(out);

		this.outgoingSynapses.add(out);
	}

	public boolean isInputNeuron() {
		return layer != null ? layer.getDepth() == NeuralNetwork.INPUT_LAYER_DEPTH : false;
	}

	@Override
	public int hashCode() {
		return 31 + incomingSynapses.hashCode() + outgoingSynapses.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Neuron) {
			Neuron n = (Neuron) o;
			return this.value == n.value && (this.layer != null) ? this.layer.getDepth() == n.layer.getDepth()
					: n.layer == null;
		}
		return false;
	}

	@Override
	public String toString() {
		return "(" + value + ")";
	}

	public double getOutput() {
		return (1 / (1 + Math.pow(Math.E, (-1 * value))));
	}

	public int getIndexInLayer() {
		return indexInLayer;
	}

	void setIndexInLayer(int indexInLayer) {
		this.indexInLayer = indexInLayer;
	}
	
	public int compareTo(Neuron o)
	{
	     return (Double.compare(o.getValue(), getValue()));
	}
}
