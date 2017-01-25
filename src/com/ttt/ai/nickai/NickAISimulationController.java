package com.ttt.ai.nickai;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.ttt.control.SimulationController;
import com.ttt.model.Brain;
import com.ttt.model.Tile;

import me.najclark.gll.ga.Individual;
import me.najclark.gll.nn.ActivationFunction;
import me.najclark.gll.nn.Layer;
import me.najclark.gll.nn.NetworkPicture;
import me.najclark.gll.nn.NeuralNetwork;

public class NickAISimulationController implements SimulationController {

	@Override
	public Brain getAI(Tile tile) {
		NeuralNetwork base = new NeuralNetwork();
		base.addLayer(new Layer(9, ActivationFunction.sigmoid));
		base.addLayer(new Layer(5, ActivationFunction.sigmoid));
		base.addLayer(new Layer(7, ActivationFunction.sigmoid));
		base.addLayer(new Layer(9, ActivationFunction.sigmoid));

		Population pop = new Population(base, 100, 0.01);
		

		while (pop.getAvgFitness() < 10 && pop.getGeneration() < 1000) {
			pop.runGeneration();
			System.out.println(pop.getGeneration() + ": " + pop.getAvgFitness());
		}
		
		return new NickAI(pop.getBestIndividual().nn);
	}
	
	public String calculateElectionWinner(ArrayList<String> votes) {
		HashMap<String, Integer> people = new HashMap<String, Integer>();

		for (String s : votes) {
			s = s.toLowerCase();
			if (people.containsKey(s)) {
				people.put(s, people.get(s) + 1);
			} else {
				people.put(s, 1);
			}
		}

		int largest = 0;
		String key = "";
		for (String p : people.keySet()) {
			if (people.get(p) > largest) {
				key = p;
				largest = people.get(p);
			} else if (people.get(p) == largest)
				key += ", " + p;
		}

		return key;
	}

}
