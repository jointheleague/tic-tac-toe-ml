package com.ttt.ai;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.ttt.model.Board;

public class Trainer {

	public static void main(String[] args) {
		JNeuralNetwork nbase = new JNeuralNetwork();
		nbase.addLayer(new JLayer(Board.BOARD_HEIGHT * Board.BOARD_WIDTH + 1));
		nbase.addLayer(new JLayer(10));
		nbase.addLayer(new JLayer(Board.BOARD_HEIGHT * Board.BOARD_WIDTH));

		nbase.makeWeightGroups();
		nbase.flush();
		
		Population pop = new Population(nbase, 100, 0.01, 3);

		pop.setDebug(false);
		pop.setExtraDebug(false);
		pop.setMaxDepth(10); // (2 for 5x5, 10 for 3x3)
		pop.setGames(3);
		
		JNetworkPicture np = new JNetworkPicture(nbase);
		np.setNodes(Color.blue);
		np.setLines(Color.blue);
		np.setNegativeLines(Color.red);
		
		JFrame frame = new JFrame();
		frame.setSize(1000, 800);
		frame.getContentPane().setLayout(new FlowLayout());
		JPanel panel = new JPanel(new GridLayout((int)Math.sqrt(pop.getPopulation().size()), (int)Math.sqrt(pop.getPopulation().size())));
		JLabel label = new JLabel(new ImageIcon(np.getNetworkImage(frame.getWidth(), frame.getHeight(), 60)));
		frame.getContentPane().add(label);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		double tiedPercent = 0;
		int gen = 0;
		while(tiedPercent < 98 && gen < 10000) {
			pop.selection();
			tiedPercent = pop.getTiedPercent();
			System.out.println(pop.getOutput());
			
			Individual ind = pop.getBestIndividual();
			np.update(ind.nn);
			BufferedImage img = np.getNetworkImage(frame.getWidth(), frame.getHeight(), 60);
			Graphics g = img.getGraphics();
			g.setColor(Color.black);
			g.drawString(pop.getOutput(), 50, 25);
			//g.drawString("Best Fitness: " + ind.fitness + " / " + pop.getMaxTiedFitness(), 50, 50);
			g.drawString("Best Individual Name: " + ind.name, 50, 50);
			label.setIcon(new ImageIcon(img));
			
			ArrayList<String> startingLetters = new ArrayList<String>();
			for(Individual i : pop.getPopulation()){
				startingLetters.add(i.name.substring(0, 1));
			}
			g.drawString("Highest Starting Letter: " + calculateElectionWinner(startingLetters), 50, 75);

			pop.makeNewGeneration();
			gen = pop.getGeneration();
			pop.clearStats();
		}
		Individual finalInd = pop.getBestIndividual();

		String dir = JOptionPane.showInputDialog("Dir for final top brain (Include all slashes): ");
		finalInd.nn.saveNN(dir + finalInd.name + "_AI");
		System.exit(0);

	}
	
	public static String calculateElectionWinner(ArrayList<String> votes) {
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