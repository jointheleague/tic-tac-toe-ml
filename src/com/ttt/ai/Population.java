package com.ttt.ai;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ttt.control.GameController;
import com.ttt.model.AIPlayer;
import com.ttt.model.Board;
import com.ttt.model.Player;
import com.ttt.model.TicTacToe;
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
	private int games = 3;

	public Population(JNeuralNetwork base, int populationSize, double mutateRate, int exponent) {
		pool = new ArrayList<Individual>();
		board = new Board();
		minimax = new Minimax(2);
		this.mutateRate = mutateRate;
		maxFitness = (int) Math.pow(10, exponent);
		maxTiedFitness = Math.pow((Board.BOARD_HEIGHT * Board.BOARD_WIDTH) / 2 + 1, exponent);
		this.exponent = exponent;

		for (int i = 0; i < populationSize; i++) {
			JNeuralNetwork nn = new JNeuralNetwork(base);
			nn.makeWeightGroups();

			Individual ind = new Individual(0.0, nn, Individual.generateName());
			pool.add(ind);
		}
	}

	public void setGames(int games) {
		this.games = games;
	}

	public void selection() {
		for (int i = 0; i < pool.size(); i++) {
			// System.out.println(pool.get(i).get(pool.get(i).keySet().toArray(new
			// Double[1])[0]));
			double fitness = selection(pool.get(i).nn);
			Individual ind = new Individual(fitness, pool.get(i).nn, pool.get(i).name);
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
		for (int i = 0; i < pool.size() * 0.49; i++) {
			Individual p1 = pickParent(null, 0);
			Individual p2 = pickParent(p1, 0);
			Individual crossed = crossover(p1, p2);
			Individual mutated = mutate(crossed, mutateRate);
			newPool.add(mutated);
			if (debug && !percent.equals(progressBar((i * 100) / pool.size()))) {
				percent = progressBar((i * 100) / pool.size());
				System.out.print("Crossover/Mutation: " + percent);
			}
		}
		for (int i = 0; i < pool.size() * 0.01; i++) {
			JNeuralNetwork nn = new JNeuralNetwork(pickParent(null, 0).nn);
			nn.makeWeightGroups();

			Individual ind = new Individual(0.0, nn, Individual.generateName());
			newPool.add(ind);
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
				+ getTiedPercent() + "% Won Percent: "
						+ getWonPercent() + "%. Average Neurons: " + avgNeurons;
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
		return (tiedPercent * 100) / (pool.size() * games);
	}

	public double getWonPercent() {
		return (wonPercent * 100) / (pool.size() * games);
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

	public void printBoard(Board board) {
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
		for (int game = 0; game < games; game++) {
			AIPlayer ai1 = new AIPlayer("Minimax");
			ai1.setNetwork(new Minimax(minimax.maxDepth));

			AIPlayer ai2 = new AIPlayer("Neural Network");
			ai2.setNetwork(new TrialAI(nn));

			TicTacToe.board = new Board();
			GameController gc = new GameController(ai1, ai2, TicTacToe.board);
			Player winner = gc.playGame();
			// printBoard(gc.getBoard());

			if (winner.getLabel().equals(ai1.getLabel())) {
				fitness += 0;
			} else if (winner.getLabel().equals(ai2.getLabel())) {
				fitness += Math.pow(10, exponent);
				wonPercent++;
			} else {
				fitness += Math.pow(5, exponent);
				tiedPercent++;
			}

			board.clearBoard();
			nn.clear();

		}
		// System.out.println(fitness);
		return fitness / games;

	}

	public static Point intToCell(int i) {
		int x = i / Board.BOARD_WIDTH;
		int y = i % Board.BOARD_HEIGHT;
		return new Point(x, y);
	}

}