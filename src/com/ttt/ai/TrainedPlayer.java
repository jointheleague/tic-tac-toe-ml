package com.ttt.ai;

import java.awt.Point;
import java.util.ArrayList;

import com.ttt.model.AINetwork;
import com.ttt.model.Board;
import com.ttt.model.Tile;

public class TrainedPlayer implements AINetwork {

	JNeuralNetwork nn;
	Tile tile;

	public TrainedPlayer(JNeuralNetwork nn, Tile tile) {
		this.nn = nn;
		this.tile = tile;
	}

	public void setNeuralNetwork(JNeuralNetwork nn) {
		this.nn = nn;
	}

	@Override
	public void performTurn(Board b) {

		JLayer inputs = new JLayer(b.BOARD_HEIGHT * b.BOARD_WIDTH + 1);
		for (int i = 0; i < inputs.getNeurons().length - 1; i++) {
			Point p = Population.intToCell(i);
			inputs.setNeuron(i, new JNeuron(b.getTile(p.x, p.y).getValue()));
		}
		inputs.setNeuron(inputs.getNeurons().length - 1, new JNeuron(1));
		
		nn.setInputs(inputs);
		nn.flush();
		
		JLayer outputs = nn.getOutputs();
		ArrayList<Integer> indexes = outputs.getHighest2Lowest();
		
		for(int i : indexes){
			Point p = Population.intToCell(i);
			if(b.getTile(p.x, p.y) == Tile.EMPTY){
				b.setTile(p.x, p.y, tile);
				return;
			}
		}

	}

	@Override
	public void setTile(Tile tile) {
		this.tile = tile;
	}

}
