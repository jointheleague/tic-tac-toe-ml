package com.ttt.ai;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

public class Individual implements Comparable<Individual>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6797004263951018815L;
	private static Random random = new Random();
	private static String consonats = "bcdfghjklmnpqrstvwxyz";
	private static String vowels = "aeiou";
	public double fitness = 0;
	public JNeuralNetwork nn;
	public String name;

	public Individual() {
		nn = new JNeuralNetwork();
	}

	public Individual(double fitness, JNeuralNetwork nn, String name) {
		this.nn = nn;
		this.fitness = fitness;
		this.name = name;
	}

	public static String generateName() {
		String name = "";
		int len = random.nextInt(5) + 3;
		for (int i = 0; i < len; i++) {
			name += pickLetter(name);
			System.out.println(name);
		}
		return name;
	}

	private static char pickLetter(String name) {
		if (name.length() >= 2) {
			int consecutive = 0;
			for (int i = name.length() - 2; i < name.length(); i++) {
				String c = String.valueOf(name.charAt(i));
				System.out.println(c);
				if (consonats.contains(c)) {
					consecutive++;
				} else if (vowels.contains(c)) {
					consecutive = 0;
				}

				if (consecutive >= 2 || random.nextFloat() < 0.1) {
					return vowels.charAt(random.nextInt(vowels.length()));
				} else {
					return consonats.charAt(random.nextInt(consonats.length()));
				}
			}
		}
		return consonats.charAt(random.nextInt(consonats.length()));
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public void setNeuralNetwork(JNeuralNetwork nn) {
		this.nn = nn;
	}

	public double getFitness() {
		return fitness;
	}

	public JNeuralNetwork getNeuralNetwork() {
		return nn;
	}

	@Override
	public int compareTo(Individual i2) {
		return Double.compare(i2.fitness, this.fitness);
	}

	@Override
	public String toString() {
		return String.valueOf(fitness);
	}

	/**
	 * {@code public boolean saveIndividual(String path)}
	 * 
	 * @param path
	 *            - The path to where the NeuralNetwork will be saved.
	 * @return Whether or not the save was successful.
	 */
	public boolean saveIndividual(String path) {
		try {
			if (!path.contains(".ind")) {
				path += ".ind";
			}
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
			return true;
		} catch (IOException i) {
			return false;
		}
	}

	/**
	 * {@code public static Individual openIndividual(String path)}
	 * 
	 * @param path
	 *            - The path to the saved Individual.
	 * @return The instance of the saved Individual.
	 */
	public static Individual openNN(String path) {
		Individual ind = null;
		try {
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			ind = (Individual) in.readObject();
			in.close();
			fileIn.close();
			return ind;
		} catch (IOException i) {
			i.printStackTrace();
			return null;
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
			return null;
		}
	}
}
