package com.ttt.ai;

public class Individual implements Comparable<Individual>{

	public double fitness = 0;
	public JNeuralNetwork nn;
	
	public Individual(){
		nn = new JNeuralNetwork();
	}
	
	public void setFitness(double fitness){
		this.fitness = fitness;
	}
	
	public void setNeuralNetwork(JNeuralNetwork nn){
		this.nn = nn;
	}
	
	public double getFitness(){
		return fitness;
	}
	
	public JNeuralNetwork getNeuralNetwork(){
		return nn;
	}
	
	public int compareTo(Individual i2){
		return Double.compare(fitness, i2.fitness);
	}
	
}
