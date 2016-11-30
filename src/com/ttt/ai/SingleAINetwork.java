package com.ttt.ai;

import java.awt.Point;

import com.ttt.model.AINetwork;
import com.ttt.model.Board;
import com.ttt.model.Tile;

public class SingleAINetwork implements AINetwork {
	private NeuralNetwork network;
	private Board board;
	private Tile tile;

	public SingleAINetwork() {
		this(generateNetwork());
	}

	public SingleAINetwork(NeuralNetwork network) {
		this.network = network;
	}

	public NeuralNetwork getNeuralNetwork() {
		return network;
	}

	private int clamp(double v, double b, double t) {
		return (int) (v <= b ? b : (v >= t ? t : v));
	}

	private void resetNeurons() {
		for (int i = 0; i < 9; i++) {
			network.getInputLayer().getNeuron(i + 1)
					.setValue(board.getTile(i / Board.BOARD_WIDTH, i % Board.BOARD_HEIGHT).asInt());
		}
	}

	public static NeuralNetwork generateNetwork() {
		NeuralNetwork net = new NeuralNetwork();

		Layer inputLayer = new Layer();
		inputLayer.addNeuron(new Neuron(1));
		for (int i = 0; i < 9; i++) {
			inputLayer.addNeuron(new Neuron(0));
		}
		net.setInputLayer(inputLayer);
		net.addLogicLayer(new Layer(new Neuron[] { new Neuron(1), new Neuron(1) }));

		Layer outputLayer = new Layer();
		for (int i = 0; i < 9; i++) {
			outputLayer.addNeuron(new Neuron(0));
		}
		net.setOutputLayer(outputLayer);
		net.connectSynapsesBetweenLayers();

		return net;
	}

	@Override
	public void performTurn(Board b) {
		this.board = b;

		resetNeurons();
		network.compute();

		int val = clamp(Math.abs(network.getOutputLayer().getNeuron(0).getValue() * 10), 0, 9);
		int x = val / 3;
		int y = val % 3;

		Point p;
		if (board.getTile(x, y) == Tile.EMPTY) {
			p = new Point(x, y);
		} else {
			if (board.nextEmpty() == null) {
				p = new Point(0, 0);
			} else {
				p = new Point(board.nextEmpty().x, board.nextEmpty().y);
			}
		}
		b.setTile(p.x, p.y, tile);
	}

	public Tile getTile() {
		return tile;
	}

	public SingleAINetwork setTile(Tile tile) {
		this.tile = tile;
		return this;
	}
}
