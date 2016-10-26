package com.ttt.ai;
import java.io.Serializable;

public class JLayer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7915683105488066923L;
	private JNeuron[] neurons;
	
	/**
	 * {@code public Layer(int neurons)}
	 * @param neurons - The number of neurons in this layer.
	 */
	public JLayer(int neurons){
		this.neurons = new JNeuron[neurons];
		
		for(int i = 0; i < this.neurons.length; i++){
			this.neurons[i] = new JNeuron(i);
		}
	}
	
	public JLayer(JNeuron[] neurons) {
		this.neurons = neurons;
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
	
	public int indexOf(JNeuron target){
		for(int i = 0; i < neurons.length; i++){
			if(target.equals(neurons[i])){
				return i;
			}
		}
		return -1;
	}
	
	public int length(){
		return neurons.length;
	}
	
	public JNeuron getHighest(){
		double highestD = Double.MIN_VALUE;
		JNeuron highest = new JNeuron();
		for(JNeuron n : neurons){
			if(n.getInput() > highestD){
				highestD = n.getInput();
				highest = n;
			}
		}
		return highest;
	}
	
	public JNeuron getLowest(){
		double lowestD = Double.MAX_VALUE;
		JNeuron lowest = new JNeuron();
		for(JNeuron n : neurons){
			if(n.getInput() < lowestD){
				lowestD = n.getInput();
				lowest = n;
			}
		}
		return lowest;
	}
	
}
