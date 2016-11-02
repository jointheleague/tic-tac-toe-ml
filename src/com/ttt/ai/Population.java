package com.ttt.ai;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.ttt.model.Board;
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
	private boolean extraDebug = false;
	private String percent = "";
	private int exponent;
	private double wonPercent = 0;
	private double tiedPercent = 0;
	private int maxDepth = 3;

	public Population(NeuralNetwork base, int populationSize, double mutateRate, int exponent) {
		pool = new ArrayList<HashMap<Double, NeuralNetwork>>();
		board = new Board();
		this.mutateRate = mutateRate;
		maxFitness = (int) Math.pow((2 * ((board.BOARD_WIDTH * board.BOARD_HEIGHT) - board.WIN_COUNT)), exponent);
		this.exponent = exponent;

		for (int i = 0; i < populationSize; i++) {
			NeuralNetwork nn = new NeuralNetwork();

			Layer[] layers = base.getAllLayers();
			nn.setInputLayer(new Layer(layers[0].getNeurons().length));
			for (int a = 1; a < layers.length - 1; a++) {
				nn.addLogicLayer(new Layer(layers[a].getNeurons().length));
			}
			nn.setOutputLayer(new Layer(layers[layers.length - 1].getNeurons().length));
			nn.connectSynapsesBetweenLayers();
			HashMap<Double, NeuralNetwork> map = new HashMap<Double, NeuralNetwork>();
			map.put(0.0, nn);
			pool.add(map);
		}
	}

	public void runGeneration() {
		for (int i = 0; i < pool.size(); i++) {
			double fitness = selection(pool.get(i).get(pool.get(i).keySet().toArray(new Double[1])[0]));
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

		int size = getHighestHalf(pool).size();
		for (int i = 0; i < size; i++) {
			HashMap<Double, NeuralNetwork> hash = new HashMap<Double, NeuralNetwork>();
			populateMatingPool(pool);
			NeuralNetwork p1 = pickParent(null, 0);
			NeuralNetwork p2 = pickParent(p1, 0);
			NeuralNetwork crossed = crossover(p1, p2);
			NeuralNetwork mutated = mutate(crossed, mutateRate);
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

		int size = (int) (pool.size() - (pool.size() * 1.0));
		pool.clear();
		ArrayList<NeuralNetwork> matingPool = getMatingPool();
		for (int i = 0; i < size; i++) {
			HashMap<Double, NeuralNetwork> hash = new HashMap<>();
			hash.put(0.0, matingPool.get(random.nextInt(matingPool.size())));
			pool.add(hash);
		}

		pool.addAll(newPool);
		System.out.println(pool.size());
		output = "Generation: " + generation + ". Average Fitness: " + avg + " Won Percent: "
				+ (wonPercent * 100 / pool.size()) + "%. Tied Percent: " + (tiedPercent * 100 / pool.size() + "%.");
		generation++;
		wonPercent = 0;
		tiedPercent = 0;
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
		this.maxDepth = depth;
	}

	public int getMaxDepth() {
		return maxDepth;
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
	public double selection(NeuralNetwork nn) {
		boolean tileWon = false;
		boolean draw = false;

		int oMoves = 0;
		double fitness = 0;

		while (!tileWon && !draw) {

			if (board.getTurn() == Tile.X) { // If X turn
				minimax(0, 1);
				board.placeAt(computersMove.x, computersMove.y, Tile.X);

			} else if (board.getTurn() == Tile.O) { // If O turn
				Layer l = new Layer();
				Tile[][] tiles = board.getTiles();
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
					int x = sortedIndexes.get(index) / Board.BOARD_WIDTH;
					int y = sortedIndexes.get(index) % Board.BOARD_HEIGHT;
					if (tiles[x][y] == Tile.EMPTY) {
						board.placeAt(x, y, Tile.O);
						break;
					}
				}
				oMoves++;
			}

			if (board.checkWin(Tile.X)) {
				fitness = oMoves;
				tileWon = true;
			} else if (board.checkWin(Tile.O)) {
				fitness = Math.pow((Board.BOARD_HEIGHT * Board.BOARD_WIDTH) - oMoves, exponent);
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
					fitness = Math.pow(oMoves, exponent);
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
		return fitness;

	}

	public static Point intToCell(int i) {
		int x = i / Board.BOARD_WIDTH;
		int y = i % Board.BOARD_HEIGHT;
		return new Point(x, y);
	}

	List<Point> availablePoints;
	Point computersMove;

	public List<Point> getAvailableStates() {
		availablePoints = new ArrayList<>();
		for (int i = 0; i < Board.BOARD_WIDTH; ++i) {
			for (int j = 0; j < Board.BOARD_HEIGHT; ++j) {
				if (board.getTile(i, j) == Tile.EMPTY) {
					availablePoints.add(new Point(i, j));
				}
			}
		}
		return availablePoints;
	}

	public int minimax(int depth, int turn) {
		if (board.checkWin(Tile.X))
			return +1;
		if (board.checkWin(Tile.O))
			return -1;

		if (depth > maxDepth) {
			return 0;
		}

		List<Point> pointsAvailable = getAvailableStates();
		if (pointsAvailable.isEmpty())
			return 0;

		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

		for (int i = 0; i < pointsAvailable.size(); ++i) {
			Point point = pointsAvailable.get(i);
			if (turn == 1) { // X's Turn
				board.setTile(point.x, point.y, Tile.X);
				int currentScore = minimax(depth + 1, 2);
				max = Math.max(currentScore, max);

				if (depth == 0)// System.out.println("Score for position
								// "+(i+1)+" = "+currentScore);
					if (currentScore >= 0) {
						if (depth == 0)
							computersMove = point;
					}
				if (currentScore == 1) {
					board.setTile(point.x, point.y, Tile.EMPTY);
					break;
				}
				if (i == pointsAvailable.size() - 1 && max < 0) {
					if (depth == 0)
						computersMove = point;
				}
			} else if (turn == 2) { // O's Turn
				board.setTile(point.x, point.y, Tile.O);
				int currentScore = minimax(depth + 1, 1);
				min = Math.min(currentScore, min);
				if (min == -1) {
					board.setTile(point.x, point.y, Tile.EMPTY);
					break;
				}
			}
			board.setTile(point.x, point.y, Tile.EMPTY); // Reset this point
		}
		return turn == 1 ? max : min;
	}

}
