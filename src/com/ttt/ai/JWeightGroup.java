package com.ttt.ai;


import java.io.Serializable;
import java.util.Random;

public class JWeightGroup implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5488310668853952534L;
	private double[] weights;
	
	public double getWeight(int index){
		return weights[index];
	}
	
	/**
	 * {@code public WeightGroup(double[] weights)}
	 * @param weights - the array of weights in the WeightGroup.
	 */
	public JWeightGroup(double[] weights){
		this.weights = weights;
	}
	
	/**
	 * {@code public static WeightGroup connectLayers(Layer l1, Layer l2)}
	 * @param l1 - The first of the layers to connect.
	 * @param l2 - The second of the layers to connect.
	 * @return A WeightGroup instance that connects the two layers.
	 */
	public static JWeightGroup connectLayers(JLayer l1, JLayer l2){
		
		Random r = new Random();
		double[] weights = new double[l1.size()*l2.size()];
		
		for(int i = 0; i < weights.length; i++){
			weights[i] = (r.nextFloat() - 0.5) * 2;
		}
		
		return new JWeightGroup(weights);
	}
	
	/**
	 * {@code public double[] getWeight()}
	 * @return An array of weights that makes up the WeightGroup.
	 */
	public double[] getWeights(){
		return weights;
	}
	
	/**
	 * {@code public void setWeights(double[] weights)}
	 * @param weights - the new weights array.
	 */
	public void setWeights(double[] weights){
		this.weights = weights;
	}
	
	/**
	 * {@code public void setWeight(int index, double weight)}
	 * @param index - the index of the weight to replace.
	 * @param weight - the new value of the weight.
	 */
	public void setWeight(int index, double weight){
		weights[index] = weight;
	}
}
