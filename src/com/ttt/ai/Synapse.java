package com.ttt.ai;

public class Synapse {
	private Neuron firingNeuron;
	private Neuron targetNeuron;
	private double weight;

	public Synapse(Neuron a, Neuron b, double weight) {
		this.firingNeuron = a;
		this.targetNeuron = b;
		this.weight = weight;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public void increaseWeight(double amt) {
		this.weight += amt;
	}

	public void decreaseWeight(double amt) {
		this.increaseWeight(-amt);
	}

	public Neuron getTargetNeuron() {
		return targetNeuron;
	}

	public void setTargetNeuron(Neuron targetNeuron) {
		this.targetNeuron = targetNeuron;
	}

	public Neuron getFiringNeuron() {
		return firingNeuron;
	}

	public void setFiringNeuron(Neuron firingNeuron) {
		this.firingNeuron = firingNeuron;
	}

	@Override
	public String toString() {
		return "[" + firingNeuron.toString() + " >{" + weight + "}> " + targetNeuron.toString() + "]";
	}
}
