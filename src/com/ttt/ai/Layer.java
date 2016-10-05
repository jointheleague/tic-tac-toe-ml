package com.ttt.ai;

public class Layer {

	private Neuron[] neurons;
	
	/**
	 * {@code public Layer(int neurons)}
	 * @param neurons - The number of neurons in this layer.
	 */
	public Layer(int neurons){
		this.neurons = new Neuron[neurons];
		
		for(int i = 0; i < this.neurons.length; i++){
			this.neurons[i] = new Neuron();
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
	public Neuron[] getNeurons(){
		return neurons;
	}
	
	/**
	 * {@code public Neuron getNeuron(int index)}
	 * @param index - index of the Neuron to return
	 * @return The Neuron at a given index.
	 */
	public Neuron getNeuron(int index){
		return neurons[index];
	}
	
	/**
	 * {@code public void setNeuron(int index, Neuron n)}
	 * @param index - index of the Neuron to replace.
	 * @param n - the Neuron to replace the existing Neuron.
	 */
	public void setNeuron(int index, Neuron n){
		neurons[index] = n;
	}
	
	
}
