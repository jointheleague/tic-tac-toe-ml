package com.ttt.ai;

import java.io.Serializable;
import java.util.Random;

public class WeightGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6200896344311127722L;
	private double[] weights;

	/**
	 * {@code public WeightGroup(double[] weights)}
	 * 
	 * @param weights
	 *            - the array of weights in the WeightGroup.
	 */
	public WeightGroup(double[] weights) {
		this.weights = weights;
	}

	/**
	 * {@code public static WeightGroup connectLayers(Layer l1, Layer l2)}
	 * 
	 * @param l1
	 *            - The first of the layers to connect.
	 * @param l2
	 *            - The second of the layers to connect.
	 * @return A WeightGroup instance that connects the two layers.
	 */
	public static WeightGroup connectLayers(Layer l1, Layer l2) {

		Random r = new Random();
		double[] weights = new double[l1.size() * l2.size()];

		for (int i = 0; i < weights.length; i++) {
			weights[i] = r.nextFloat();
		}

		return new WeightGroup(weights);
	}

	/**
	 * {@code public double[] getWeight()}
	 * 
	 * @return An array of weights that makes up the WeightGroup.
	 */
	public double[] getWeights() {
		return weights;
	}

	/**
	 * {@code public void setWeights(double[] weights)}
	 * 
	 * @param weights
	 *            - the new weights array.
	 */
	public void setWeights(double[] weights) {
		this.weights = weights;
	}

	/**
	 * {@code public void setWeight(int index, double weight)}
	 * 
	 * @param index
	 *            - the index of the weight to replace.
	 * @param weight
	 *            - the new value of the weight.
	 */
	public void setWeight(int index, double weight) {
		weights[index] = weight;
	}
}
