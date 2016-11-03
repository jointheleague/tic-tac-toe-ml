package com.ttt.ai;
import java.io.Serializable;

public class JLayer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7915683105488066923L;
	private JNeuron[] neurons;
	
	@Override
	public String toString(){
		String output = "{";
		for(JNeuron n : neurons){
			output += n + ", ";
		}
		output += "}";
		return output;
	}
	
	/**
	 * {@code public Layer(int neurons)}
	 * @param neurons - The number of neurons in this layer.
	 */
	public JLayer(int neurons){
		this.neurons = new JNeuron[neurons];
		
		for(int i = 0; i < this.neurons.length; i++){
			this.neurons[i] = new JNeuron();
		}
	}
	
	/**
	 * {@code public int size()}
	 * @return The number of Neurons in the layer.
	 */
	public int size(){
		return neurons.length;
	}
	
	/**
	 * {@code public Neuron[] getNeurons()}
	 * @return An array of Neurons in the layer.
	 */
	public JNeuron[] getNeurons(){
		return neurons;
	}
	
	/**
	 * {@code public Neuron getNeuron(int index)}
	 * @param index - index of the Neuron to return
	 * @return The Neuron at a given index.
	 */
	public JNeuron getNeuron(int index){
		return neurons[index];
	}
	
	/**
	 * {@code public void setNeuron(int index, Neuron n)}
	 * @param index - index of the Neuron to replace.
	 * @param n - the Neuron to replace the existing Neuron.
	 */
	public void setNeuron(int index, JNeuron n){
		neurons[index] = n;
	}
	
	
}
