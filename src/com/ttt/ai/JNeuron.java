package com.ttt.ai;
import java.io.Serializable;

public class JNeuron implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -798726926214283987L;
	private double input;
	private int index;
	
	/**
	 * {@code public Neuron(double input)}
	 * @param input - the input of the Neuron.
	 */
	public JNeuron(double input, int index){
		this.input = input;
		this.index = index;
	}
	
	public JNeuron(int index){
		this.index = index;
	}
	
	/**
	 * {@code public Neuron()}
	 */
	public JNeuron(){
		this.input = 0;
		this.index = 0;
	}
	
	public void setIndex(int index){
		this.index = index;
	}
	
	public int getIndex(){
		return index;
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
