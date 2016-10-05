package com.ttt.ai;

public class Neuron {
	
	private double input;
	
	/**
	 * {@code public Neuron(double input)}
	 * @param input - the input of the Neuron.
	 */
	public Neuron(double input){
		this.input = input;
	}
	
	/**
	 * {@code public Neuron()}
	 */
	public Neuron(){
		this.input = 0;
	}
	
	/**
	 * {@code public void setInput()}
	 * @param input - the input of the Neuron.
	 */
	public void setInput(double input){
		this.input = input;
	}
	
	/**
	 * {@code public double getInput()}
	 * @return The given input of the Neuron.
	 */
	public double getInput(){
		return input;
	}

	/**
	 * {@code public double getOutput()}
	 * @return The result of the sigmoid function, based on the Neuron's set input.
	 */
	public double getOutput(){
		return (1/( 1 + Math.pow(Math.E,(-1*input))));
	}
	
}
