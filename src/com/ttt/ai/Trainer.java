package com.ttt.ai;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
		pop.setMaxDepth(2); // Dosen't matter for 3 x 3 board
		
		JNetworkPicture np = new JNetworkPicture(nbase);
		np.setNodes(Color.blue);
		np.setLines(Color.blue);
		np.setNegativeLines(Color.red);
		
		JFrame frame = new JFrame();
		frame.setSize(1000, 1000);
		frame.getContentPane().setLayout(new FlowLayout());
		JLabel label = new JLabel(new ImageIcon(np.getNetworkImage(frame.getWidth(), frame.getHeight(), 60)));
		frame.getContentPane().add(label);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		double tiedPercent = 0;
		int gen = 0;
		while(tiedPercent < 95 && gen < 10000) {
			pop.selection();
			tiedPercent = pop.getTiedPercent();
			System.out.println(pop.getOutput());
			
			Individual ind = pop.getBestIndividual();
			np.update(ind.nn);
			BufferedImage img = np.getNetworkImage(frame.getWidth(), frame.getHeight(), 60);
			Graphics g = img.getGraphics();
			g.setColor(Color.black);
			g.drawString(pop.getOutput(), 50, 25);
			g.drawString("Best Fitness: " + ind.fitness, 50, 50);
			label.setIcon(new ImageIcon(img));

			pop.makeNewGeneration();
			gen = pop.getGeneration();
			pop.clearStats();
		}
		ArrayList<Individual> finals = pop.getPopulation();

		String dir = JOptionPane.showInputDialog("Dir for final brains (Include all slashes): ");
		int index = 0;
		for(Individual i : finals){
			i.nn.saveNN(dir + "nn" + index);
			index++;
		}
		System.exit(0);

	}

}
