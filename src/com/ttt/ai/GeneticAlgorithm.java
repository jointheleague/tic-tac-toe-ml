package com.ttt.ai;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import com.ttt.model.Board;
import com.ttt.model.TicTacToe;
import com.ttt.model.Tile;
import com.ttt.view.RenderService;
import com.ttt.view.WindowIcon;

public class GeneticAlgorithm {

	public static void main(String[] args) {

		NeuralNetwork nbase = new NeuralNetwork();
		nbase.setInputLayer(new Layer(new Neuron[] { new Neuron(1), new Neuron(0), new Neuron(0), new Neuron(-2),
				new Neuron(4), new Neuron(1), new Neuron(0), new Neuron(2), new Neuron(-1), new Neuron(1) }));
		nbase.addLogicLayer(new Layer(15));
		nbase.setOutputLayer(new Layer(9));

		nbase.connectSynapsesBetweenLayers();
		nbase.compute();

		NeuralNetwork nbase2 = new NeuralNetwork();
		nbase2.setInputLayer(new Layer(new Neuron[] { new Neuron(1), new Neuron(0), new Neuron(0), new Neuron(-2),
				new Neuron(4), new Neuron(1), new Neuron(0), new Neuron(2), new Neuron(-1), new Neuron(1) }));
		nbase2.addLogicLayer(new Layer(15));
		nbase2.setOutputLayer(new Layer(9));

		nbase2.connectSynapsesBetweenLayers();
		nbase2.compute();

		NeuralNetwork crossed = crossover(nbase, nbase2);
		crossed.compute();

		System.out.println("First nn base output: " + nbase.getOutputLayer().getHighest().getValue());

		NeuralNetwork nmutated = mutate(nbase, 0.1);
		nmutated.compute();

		System.out.println("First crossover base output: " + crossed.getOutputLayer().getHighest().getValue());

		System.out.println("Crossover nn fitness: " + simulate(crossed));
	}

	public static JNeuralNetwork jmutate(JNeuralNetwork jnn, double mutateRate) {
		Random r = new Random();

		JNeuralNetwork mutated = new JNeuralNetwork();
		for (JLayer l : jnn.getLayers()) {
			mutated.addLayer(l);
		}

		ArrayList<JWeightGroup> weights = new ArrayList<>();
		for (JWeightGroup wg : jnn.getWeightGroups()) {
			for (int i = 0; i < wg.getWeights().length; i++) {
				if (r.nextDouble() < mutateRate) {
					wg.setWeight(i, r.nextDouble());
				}
			}
			weights.add(wg);
		}
		mutated.setWeightGroups(weights);
		return mutated;
	}

	public static NeuralNetwork mutate(NeuralNetwork nn, double mutateRate) {
		Random r = new Random();

		for (int i = 0; i < nn.getAllLayers().length - 1; i++) {
			Layer l = nn.getAllLayers()[i];
			for (Neuron n : l.getNeurons()) {
				for (Synapse s : n.getOutgoingSynapses()) {
					if (r.nextDouble() < mutateRate) {
						s.setWeight(r.nextDouble());
					}
				}
			}
		}
		return nn;
	}

	public static NeuralNetwork crossover(NeuralNetwork nn, NeuralNetwork nn2) {
		Random r = new Random();

		for (int i = 0; i < nn.getAllLayers().length - 1; i++) {
			Layer l = nn.getAllLayers()[i];
			for (int a = 0; a < l.getNeurons().length; a++) {
				Neuron n = l.getNeuron(a);
				for (int d = 0; d < n.getOutgoingSynapses().length; d++) {
					Synapse s = n.getOutgoingSynapses()[d];
					if (r.nextBoolean()) {
						s.setWeight(nn2.getAllLayers()[i].getNeuron(a).getOutgoingSynapses()[d].getWeight());
					}
				}
			}
		}
		return nn;
	}

	public static double simulate(NeuralNetwork nn) {
		Board b = new Board(TicTacToe.tiles);
		//setupGraphics(b);
		boolean tileWon = false;
		boolean draw = false;

		int oMoves = 0;
		double fitness = 0;

		while (!tileWon && !draw) {
			if (b.getTurn() == Tile.X) { // If X turn
				Point p = pickRandom(b);
				b.placeAt(p.x, p.y, Tile.X);

			} else { // If O turn
				Layer l = new Layer();
				Tile[][] tiles = b.getTiles();
				for (int x = 0; x < tiles.length; x++) {
					for (int y = 0; y < tiles[0].length; y++) {
						if (tiles[x][y] == Tile.EMPTY) {
							l.addNeuron(new Neuron(0));
						} else if (tiles[x][y] == Tile.X) {
							l.addNeuron(new Neuron(2));
						} else { // If tile is O
							l.addNeuron(new Neuron(-1));
						}
					}
				}
				l.addNeuron(new Neuron(1)); //constant
				nn.setInputLayer(l);
				nn.compute();

				System.out.println(nn.getInputLayer());

				ArrayList<Integer> sortedIndexes = nn.getOutputLayer().getHighest2Lowest();
				boolean placed = false;
				int index = 0;
				while (!placed && index < Board.BOARD_HEIGHT * Board.BOARD_WIDTH) {
					int x = sortedIndexes.get(index) % Board.BOARD_WIDTH;
					int y = sortedIndexes.get(index) / Board.BOARD_HEIGHT;
					if (tiles[x][y] == Tile.EMPTY) {
						b.placeAt(x, y, Tile.O);
						placed = true;
					}
					index++;
				}
				 if (!placed){
				 Point p = pickRandom(b);
				 b.placeAt(p.x, p.y, Tile.O);
				 }
				oMoves++;
			}

			if (b.checkWin(Tile.X)) {
				fitness = oMoves;
				tileWon = true;
				System.out.println("X Won!");
			} else if (b.checkWin(Tile.O)) {
				fitness = Math.pow((Board.BOARD_HEIGHT * Board.BOARD_WIDTH) - oMoves, 2);
				tileWon = true;
				System.out.println("O Won!");
			} else {
				boolean emptyTile = false;
				Tile[][] tiles = b.getTiles();
				for (Tile[] tiles1 : tiles) {
					for (Tile tile : tiles1) {
						if (tile == Tile.EMPTY) {
							emptyTile = true;
							break;
						}
					}
				}
				if (!emptyTile) {// The board is full
					fitness = oMoves;
					draw = true;
					System.out.println("Tie!");
				}
			}

			b.switchTurn();
		}

		return fitness;

	}
	
	public static void setupGraphics(Board b) {
		JFrame frame = new JFrame("Tic Tac Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WindowIcon.setupIcons(frame);
		frame.add(new RenderService().getContents());
		frame.setVisible(true);
		frame.setSize(RenderService.PANEL_WIDTH, RenderService.PANEL_HEIGHT + frame.getInsets().top);
	}

	public static Point pickRandom(Board b) {
		Random r = new Random();
		Tile[][] tiles = b.getTiles();

		int trys = 100;
		while (trys > 0) {
			int x = r.nextInt(tiles.length);
			int y = r.nextInt(tiles[0].length);
			Tile t = tiles[x][y];
			if (t == Tile.EMPTY) {
				return new Point(x, y);
			}
			trys--;
		}
		return null;
	}

}
