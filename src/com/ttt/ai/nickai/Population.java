package com.ttt.ai.nickai;

import java.util.ArrayList;

import com.ttt.ai.minimax.MinimaxSimulationController;
import com.ttt.control.GameController;
import com.ttt.model.Board;
import com.ttt.model.Player;
import com.ttt.model.Tile;

import me.najclark.gll.ga.GeneticAlgorithm;
import me.najclark.gll.ga.Individual;
import me.najclark.gll.nn.NeuralNetwork;

public class Population extends GeneticAlgorithm {

	public Population(NeuralNetwork base, int popSize, double mutateRate) {
		super();
		initialize(mutateRate);
		for (int i = 0; i < popSize; i++) {
			NeuralNetwork nn = new NeuralNetwork(base);
			nn.makeWeightGroups();
			pool.add(new Individual(0.0, nn, Individual.generateName()));
		}

		this.mutateRate = mutateRate;
	}

	@Override
	public void clearStats() {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeNewGeneration() {
		ArrayList<Individual> newPool = new ArrayList<Individual>();

		populateMatingPool(pool);
		for (int i = 0; i < pool.size(); i++) {
			Individual p1 = pickParent(null, 0);
			Individual p2 = pickParent(p1, 0);
			Individual child = mutate(crossover(p1, p2), mutateRate);

			newPool.add(child);
		}

		pool.clear();
		pool.addAll(newPool);
		generation++;
	}

	@Override
	public double simulate(NeuralNetwork nn) {
		Player minimax = new Player("minimax", Tile.X, new MinimaxSimulationController().getAI(Tile.X));
		Player aiTest = new Player(nn.getId(), Tile.O, new NickAI(nn));
		GameController gc = new GameController(minimax, aiTest, new Board());
		Player winner = gc.playGame();

		if (winner == null) { //Tie
			return 5;
		} else if (winner.getLabel().equals(aiTest.getLabel())) { // NeuralNetwork
			return 10;
		} else if (winner.getLabel().equals(minimax.getLabel())) { // Minimax
			return 1;
		}
		return 1;
	}

}
