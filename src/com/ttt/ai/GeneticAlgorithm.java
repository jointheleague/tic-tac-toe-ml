package com.ttt.ai;

public class GeneticAlgorithm {

	public static void main(String[] args) {
		NeuralNetwork base = new NeuralNetwork();
		
		base.addLayer(new Layer(9));
		base.addLayer(new Layer(10));
		base.addLayer(new Layer(9));
		
		base.makeWeightGroups();
		
		Population p = new Population(base, 200, 0.01);
		double highestFit = p.draw();
		while(highestFit < 144){
			p.draw();
		}
		
	}

}
