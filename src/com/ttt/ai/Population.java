package com.ttt.ai;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ttt.model.Board;
import com.ttt.model.Tile;
import com.ttt.model.TilePosition;

public class Population extends GeneticAlgorithm {

	private ArrayList<Individual> pool;
	private Board board;
	private int maxFitness = 0;
	private Random random = new Random();
	private double mutateRate;
	private int generation = 0;
	private String output = "";
	private boolean debug = false;
	private boolean extraDebug = false;
	private String percent = "";
	private int exponent;
	private double wonPercent = 0;
	private double tiedPercent = 0;
	private double avgFitness = 0;
	private double avgNeurons = 0;
	private double maxTiedFitness = 0;
	private Minimax minimax;

	public Population(JNeuralNetwork base, int populationSize, double mutateRate, int exponent) {
		pool = new ArrayList<Individual>();
		board = new Board();
		minimax = new Minimax(2, Tile.X);
		this.mutateRate = mutateRate;
		maxFitness = (int) Math.pow((2 * ((Board.BOARD_WIDTH * Board.BOARD_HEIGHT) - board.WIN_COUNT)), exponent);
		maxTiedFitness = Math.pow((Board.BOARD_HEIGHT * Board.BOARD_WIDTH) / 2 + 1, exponent);
		this.exponent = exponent;

		for (int i = 0; i < populationSize; i++) {
			JNeuralNetwork nn = new JNeuralNetwork(base);
			nn.makeWeightGroups();

			Individual ind = new Individual(0.0, nn);
			pool.add(ind);
		}
	}

	public void selection() {
		for (int i = 0; i < pool.size(); i++) {
			// System.out.println(pool.get(i).get(pool.get(i).keySet().toArray(new
			// Double[1])[0]));
			double fitness = selection(pool.get(i).nn);
			Individual ind = new Individual(fitness, pool.get(i).nn);
			pool.set(i, ind);
			if (debug && !percent.equals(progressBar((i * 100) / pool.size()))) {
				percent = progressBar((i * 100) / pool.size());
				System.out.print("Selection: " + percent);
			}
		}
		if (debug) {
			System.out.println("Selection Done!");
		}
	}

	public void makeNewGeneration() {
		ArrayList<Individual> newPool = new ArrayList<Individual>();

		populateMatingPool(pool);
		for (int i = 0; i < pool.size() / 2; i++) {
			JNeuralNetwork p1 = pickParent(null, 0);
			JNeuralNetwork p2 = pickParent(p1, 0);
			JNeuralNetwork crossed = crossover(p1, p2);
			JNeuralNetwork mutated = mutate(crossed, mutateRate);
			Individual ind = new Individual(0.0, mutated);
			newPool.add(ind);
			if (debug && !percent.equals(progressBar((i * 100) / pool.size()))) {
				percent = progressBar((i * 100) / pool.size());
				System.out.print("Crossover/Mutation: " + percent);
			}
		}
		newPool.addAll(getHighestHalf(pool));
		if (debug) {
			System.out.println("Crossover/Mutation Done!");
		}

		double avgFitness = 0;
		double avgNeurons = 0;
		for (int i = 0; i < pool.size(); i++) {
			avgFitness += pool.get(i).fitness;
			avgNeurons += pool.get(i).nn.getTotalNeurons();
		}

		avgFitness /= pool.size();
		avgNeurons /= pool.size();
		this.avgFitness = avgFitness;
		this.avgNeurons = avgNeurons;

		pool.clear();
		pool.addAll(newPool);
		// System.out.println(Arrays.toString(pool.toArray()));
		output = "Generation: " + generation + ". Average Fitness: " + avgFitness + " Tied Percent: "
				+ (tiedPercent * 100 / (pool.size() * 3) + "%. Average Neurons: " + avgNeurons);
		generation++;
	}

	public void clearStats() {
		wonPercent = 0;
		tiedPercent = 0;
	}

	public void runGeneration() {

		selection();

		makeNewGeneration();

		clearStats();
	}

	public double getMaxTiedFitness() {
		return maxTiedFitness;
	}

	public Individual getBestIndividual() {
		return getSorted(pool).get(0);
	}

	public ArrayList<Individual> getPopulation() {
		return pool;
	}

	public double getAvgFitness() {
		return avgFitness;
	}

	public double getAvgNeurons() {
		return avgNeurons;
	}

	public double getTiedPercent() {
		return (tiedPercent * 100) / (pool.size() * 3);
	}

	public int getGeneration() {
		return generation;
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

	public void setMaxDepth(int depth) {
		minimax.setMaxDepth(depth);
	}

	public int getMaxDepth() {
		return minimax.getMaxDepth();
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

	public void setExtraDebug(boolean extraDebug) {
		this.extraDebug = extraDebug;
	}

	public Point pickRandom() {
		Tile[][] tiles = board.getTiles();

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

	public void printBoard() {
		Tile[][] tiles = board.getTiles();

		System.out.println();
		for (int x = 0; x < board.BOARD_HEIGHT; x++) {
			for (int y = 0; y < board.BOARD_WIDTH; y++) {
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
		if (board.checkWin(Tile.X)) {
			System.out.println("X Won!");
		} else if (board.checkWin(Tile.O)) {
			System.out.println("O Won!");
		}
	}

	@Override
	public double selection(JNeuralNetwork nn) {

		double fitness = 0;
		for (int game = 0; game < 3; game++) {
			board = new Board();
			boolean tileWon = false;
			boolean draw = false;

			int oMoves = 0;
			
			for(int i = 0; i < random.nextInt(5); i++){
				board.switchTurn();
			}

			while (!tileWon && !draw) {

				if (board.getTurn() == Tile.X) { // If X turn
					TilePosition tp = minimax.getNextMove(board.getTiles());
					board.setTile(tp.getX(), tp.getY(), Tile.X);

				} else if (board.getTurn() == Tile.O) { // If O turn
					JLayer l = new JLayer(Board.BOARD_WIDTH * Board.BOARD_HEIGHT + 1);
					Tile[][] tiles = board.getTiles();
					int i = 0;
					for (int x = 0; x < tiles.length; x++) {
						for (int y = 0; y < tiles[0].length; y++) {
							l.setNeuron(i, new JNeuron(tiles[x][y].getValue()));
							i++;
						}
					}
					l.setNeuron(Board.BOARD_WIDTH * Board.BOARD_HEIGHT, new JNeuron(1)); // constant
					nn.setInputs(l);
					nn.flush();

					JLayer output = nn.getOutputs();
					ArrayList<Integer> sortedIndexes = output.getHighest2Lowest();
					for (int index = 0; index < Board.BOARD_WIDTH * Board.BOARD_HEIGHT; index++) {
						int x = sortedIndexes.get(index) / Board.BOARD_WIDTH;
						int y = sortedIndexes.get(index) % Board.BOARD_HEIGHT;
						if (tiles[x][y] == Tile.EMPTY) {
							board.setTile(x, y, Tile.O);
							break;
						} else {
							// fitness -= Math.pow(2, exponent);
						}
					}
					oMoves++;
				}

				if (board.checkWin(Tile.X)) {
					fitness += oMoves;
					tileWon = true;
				} else if (board.checkWin(Tile.O)) {
					fitness += Math.pow((Board.BOARD_HEIGHT * Board.BOARD_WIDTH) - oMoves, exponent);
					tileWon = true;
					wonPercent++;
				} else {
					boolean emptyTile = false;
					Tile[][] tiles = board.getTiles();
					for (Tile[] tiles1 : tiles) {
						for (Tile tile : tiles1) {
							if (tile == Tile.EMPTY) {
								emptyTile = true;
								break;
							}
						}
					}
					if (!emptyTile) {// The board is full
						fitness += Math.pow(oMoves, exponent);
						draw = true;
						tiedPercent++;
					}
				}

				board.switchTurn();
				// printBoard();
			}

			if (extraDebug) {
				printBoard();
			}

			board.clearBoard();
			nn.clear();

		}
		// System.out.println(fitness);
		return fitness / 3;

	}

	public static Point intToCell(int i) {
		int x = i / Board.BOARD_WIDTH;
		int y = i % Board.BOARD_HEIGHT;
		return new Point(x, y);
	}

}
