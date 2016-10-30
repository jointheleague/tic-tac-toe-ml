package com.ttt.ai;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.ttt.model.Board;
import com.ttt.model.TicTacToe;
import com.ttt.model.Tile;

public class Population extends GeneticAlgorithm {

	private ArrayList<HashMap<Double, NeuralNetwork>> pool;
	private Board board;
	private int maxFitness = 0;
	private Random random = new Random();
	private double mutateRate;
	private int generation = 0;
	private String output = "";
	private boolean debug = false;
	private String percent = "";
	private int exponent;

	public Population(NeuralNetwork base, int populationSize, double mutateRate, int exponent) {
		pool = new ArrayList<HashMap<Double, NeuralNetwork>>();
		board = new Board(TicTacToe.tiles);
		this.mutateRate = mutateRate;
		maxFitness = (int) Math.pow((2 * ((board.BOARD_WIDTH * board.BOARD_HEIGHT) - board.WIN_COUNT)), exponent);
		this.exponent = exponent;

		for (int i = 0; i < populationSize; i++) {
			NeuralNetwork nn = new NeuralNetwork();

			Layer[] layers = base.getAllLayers();
			nn.setInputLayer(layers[0]);
			for (int a = 1; a < layers.length - 1; a++) {
				nn.addLogicLayer(layers[a]);
			}
			nn.setOutputLayer(layers[layers.length - 1]);
			nn.connectSynapsesBetweenLayers();
			HashMap<Double, NeuralNetwork> map = new HashMap<Double, NeuralNetwork>();
			map.put(0.0, nn);
			pool.add(map);
		}
	}

	public void runGeneration() {
		for (int i = 0; i < pool.size(); i++) {
			double fitness = selection(pool.get(i).get(0.0));
			HashMap<Double, NeuralNetwork> hash = new HashMap<Double, NeuralNetwork>();
			hash.put(fitness, pool.get(i).get(0.0));
			pool.set(i, hash);
			if (debug && !percent.equals(progressBar((i * 100) / pool.size()))) {
				percent = progressBar((i * 100) / pool.size());
				System.out.println("Selection: " + percent);
			}
		}
		if (debug) {
			System.out.println("Selection Done!");
		}

		ArrayList<HashMap<Double, NeuralNetwork>> newPool = new ArrayList<HashMap<Double, NeuralNetwork>>();

		for (int i = 0; i < pool.size(); i++) {
			HashMap<Double, NeuralNetwork> hash = new HashMap<Double, NeuralNetwork>();
			NeuralNetwork p1 = acceptReject(pool, maxFitness);
			//System.out.println("Parent 1 found.");
			NeuralNetwork p2 = acceptReject(pool, maxFitness);
			//System.out.println("Parent 1 found.");
			NeuralNetwork crossed = crossover(p1, p2);
			//System.out.println("Child born.");
			NeuralNetwork mutated = mutate(crossed, mutateRate);
			//System.out.println("Child mutated!!!");
			//System.out.println();
			hash.put(0.0, mutated);
			newPool.add(hash);
			if (debug && !percent.equals(progressBar((i * 100) / pool.size()))) {
				percent = progressBar((i * 100) / pool.size());
				System.out.println("Crossover/Mutation: " + percent);
			}
		}
		if (debug) {
			System.out.println("Crossover/Mutation Done!");
		}

		double avg = 0;
		for (int i = 0; i < pool.size(); i++) {
			avg += pool.get(i).keySet().toArray(new Double[1])[0];
		}

		avg /= pool.size();

		pool.removeAll(pool);
		pool.addAll(newPool);
		output = "Generation: " + generation + ". Average Fitness: " + avg;
		generation++;
	}

	public String progressBar(int progress) {
		String percent = "|";
		for (int i = 0; i < 10; i++) {
			if (progress >= 10) {
				percent += "=";
			} else {
				percent += "-";
			}

			progress -= 10;
		}
		percent += "|\r";
		return percent;
	}

	public String getProgress() {
		return percent;
	}

	public String getOutput() {
		return output;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public Point pickRandom(Board b) {
		Tile[][] tiles = b.getTiles();

		int trys = 100;
		while (trys > 0) {
			int x = random.nextInt(tiles.length);
			int y = random.nextInt(tiles[0].length);
			Tile t = tiles[x][y];
			if (t == Tile.EMPTY) {
				return new Point(x, y);
			}
			trys--;
		}
		return null;
	}

	public void printBoard(Board b) {
		Tile[][] tiles = b.getTiles();

		System.out.println();
		for (int x = 0; x < b.BOARD_HEIGHT; x++) {
			for (int y = 0; y < b.BOARD_WIDTH; y++) {
				if (tiles[x][y] == Tile.EMPTY) {
					System.out.print(" - ");
				} else if (tiles[x][y] == Tile.X) {
					System.out.print(" X ");
				} else { // If tile is O
					System.out.print(" O ");
				}
			}
			System.out.println();
		}
	}

	@Override
	public double selection(NeuralNetwork nn) {
		Board b = new Board(TicTacToe.tiles);
		boolean tileWon = false;
		boolean draw = false;

		int oMoves = 0;
		double fitness = 0;

		while (!tileWon && !draw) {

			if (b.getTurn() == Tile.X) { // If X turn
				Point p = pickRandom(b);
				b.placeAt(p.x, p.y, Tile.X);

			} else if (b.getTurn() == Tile.O) { // If O turn
				Layer l = new Layer();
				Tile[][] tiles = b.getTiles();
				for (int x = 0; x < tiles.length; x++) {
					for (int y = 0; y < tiles[0].length; y++) {
						if (tiles[x][y] == Tile.EMPTY) {
							l.addNeuron(new Neuron(0));
						} else if (tiles[x][y] == Tile.X) {
							l.addNeuron(new Neuron(1));
						} else { // If tile is O
							l.addNeuron(new Neuron(-1));
						}
					}
				}
				l.addNeuron(new Neuron(1)); // constant
				nn.setInputs(l);
				nn.compute();

				Layer output = nn.getOutputLayer();
				ArrayList<Integer> sortedIndexes = output.getHighest2Lowest();
				for (int index = 0; index < Board.BOARD_WIDTH * Board.BOARD_HEIGHT; index++) {
					int x = sortedIndexes.get(index) % Board.BOARD_WIDTH;
					int y = sortedIndexes.get(index) / Board.BOARD_HEIGHT;
					if (tiles[x][y] == Tile.EMPTY) {
						b.placeAt(x, y, Tile.O);
						break;
					}
				}
				oMoves++;
			}

			if (b.checkWin(Tile.X)) {
				fitness = oMoves;
				tileWon = true;
			} else if (b.checkWin(Tile.O)) {
				fitness = Math.pow((Board.BOARD_HEIGHT * Board.BOARD_WIDTH) - oMoves, exponent);
				tileWon = true;
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
					fitness = Math.pow(oMoves, exponent);
					draw = true;
				}
			}

			b.switchTurn();
		}

		b.clearBoard();
		return fitness;

	}

}
