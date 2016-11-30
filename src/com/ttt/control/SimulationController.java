package com.ttt.control;

import java.util.ArrayList;

import com.ttt.ai.Minimax;
import com.ttt.ai.NeuralNetwork;
import com.ttt.ai.SingleAINetwork;
import com.ttt.model.AIPlayer;
import com.ttt.model.Board;
import com.ttt.model.Player;
import com.ttt.model.Tile;

public class SimulationController {
	private static NeuralNetwork mutation;

	public static SingleAINetwork learn(int perGeneration, int generations) {
		mutation = SingleAINetwork.generateNetwork();

		for (int i = 0; i < generations; i++) {
			simulate(perGeneration);
		}
		return new SingleAINetwork(mutation);
	}

	private static void simulate(int perGeneration) {
		ArrayList<NeuralNetwork> successfulNetworks = new ArrayList<>();
		for (int i = 0; i < perGeneration; i++) {
			Board board = new Board();

			SingleAINetwork netInterface = new SingleAINetwork(mutation.mutate(0.2, 1)).setTile(Tile.X);
			Player p1 = new AIPlayer("Player 1", netInterface);
			Player p2 = new AIPlayer("Player 2", new Minimax(2, Tile.O));
			Player winner = new GameController(p1, p2, board).playGame();
			if (winner == p1) {
				successfulNetworks.add(netInterface.getNeuralNetwork());
			}
		}
		if (successfulNetworks.size() > 0) {
			mutation = successfulNetworks.get(0);
			for (int n = 1; n < successfulNetworks.size(); n++) {
				NeuralNetwork net = successfulNetworks.get(n);
				for (int neuron = 0; neuron < net.getInputLayer().getNeurons().length; neuron++) {
					for (int synapse = 0; synapse < net.getInputLayer().getNeuron(neuron)
							.getOutgoingSynapses().length; synapse++) {
						mutation.getInputLayer().getNeuron(neuron).getOutgoingSynapses()[synapse].setWeight(
								mutation.getInputLayer().getNeuron(neuron).getOutgoingSynapses()[synapse].getWeight()
										+ (net.getInputLayer().getNeuron(neuron).getOutgoingSynapses()[synapse]
												.getWeight() * Math.random()));
					}
				}
			}
		}
	}
}
