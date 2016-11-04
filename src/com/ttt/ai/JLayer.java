package com.ttt.ai;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class JLayer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7915683105488066923L;
	private ArrayList<JNeuron> neurons;
	
	@Override
	public String toString(){
		String output = "{";
		for(JNeuron n : neurons){
			output += n + ", ";
		}
		output += "}";
		return output;
	}
	
	public JNeuron getHighest() {
		JNeuron highest = new JNeuron(Double.MIN_VALUE);
		for (JNeuron n : neurons) {
			if (n.getValue() > n.getValue()) {
				highest = n;
			}
		}
		return highest;
	}

	public JNeuron getLowest() {
		JNeuron lowest = new JNeuron(Double.MAX_VALUE);
		for (JNeuron n : neurons) {
			if (n.getValue() < n.getValue()) {
				lowest = n;
			}
		}
		return lowest;
	}

	public ArrayList<Integer> getHighest2Lowest() {
		ArrayList<JNeuron> copy = new ArrayList<JNeuron>();
		copy.addAll(neurons);
		Collections.sort(copy);

		ArrayList<Integer> sorted = new ArrayList<Integer>();
		for (JNeuron n : copy) {
			sorted.add(neurons.indexOf(n));
		}

		return sorted;
	}
	
	/**
	 * {@code public Layer(int neurons)}
	 * @param neurons - The number of neurons in this layer.
	 */
	public JLayer(int neurons){
		this.neurons = new ArrayList<JNeuron>(neurons);
		for(int i = 0; i < neurons; i++){
			this.neurons.add(new JNeuron());
		}
	}
	
	public JLayer(){
		this(0);
	}
	
	/**
	 * {@code public int size()}
	 * @return The number of Neurons in the layer.
	 */
	public int size(){
		return neurons.size();
	}
	
	/**
	 * {@code public Neuron[] getNeurons()}
	 * @return An array of Neurons in the layer.
	 */
	public JNeuron[] getNeurons(){
		return neurons.toArray(new JNeuron[neurons.size()]);
	}
	
	/**
	 * {@code public Neuron getNeuron(int index)}
	 * @param index - index of the Neuron to return
	 * @return The Neuron at a given index.
	 */
	public JNeuron getNeuron(int index){
		return neurons.get(index);
	}
	
	/**
	 * {@code public void setNeuron(int index, Neuron n)}
	 * @param index - index of the Neuron to replace.
	 * @param n - the Neuron to replace the existing Neuron.
	 */
	public void setNeuron(int index, JNeuron n){
		neurons.set(index, n);
	}
	
	
}
