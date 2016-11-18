package com.ttt.ai;

import java.util.ArrayList;

import com.ttt.model.Board;
import com.ttt.model.Tile;
import com.ttt.model.TilePosition;

public class Brain implements com.ttt.model.AI{

	JNeuralNetwork jnn;
	
	public void setNeuralNetwork(JNeuralNetwork jnn){
		this.jnn = jnn;
	}
	
	@Override
	public TilePosition getNextMove(Tile[][] tiles) {
		JLayer l = new JLayer(Board.BOARD_WIDTH * Board.BOARD_HEIGHT + 1);
		int i = 0;
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[0].length; y++) {
				l.setNeuron(i, new JNeuron(tiles[x][y].getValue()));
				i++;
			}
		}
		l.setNeuron(Board.BOARD_WIDTH * Board.BOARD_HEIGHT, new JNeuron(1)); // constant
		jnn.setInputs(l);
		jnn.flush();

		JLayer output = jnn.getOutputs();
		ArrayList<Integer> sortedIndexes = output.getHighest2Lowest();
		for (int index = 0; index < Board.BOARD_WIDTH * Board.BOARD_HEIGHT; index++) {
			int x = sortedIndexes.get(index) / Board.BOARD_WIDTH;
			int y = sortedIndexes.get(index) % Board.BOARD_HEIGHT;
			if (tiles[x][y] == Tile.EMPTY) {
				return new TilePosition(Tile.EMPTY, x, y);
			} else {
				// fitness -= Math.pow(2, exponent);
			}
		}
		return null;
	}

}
