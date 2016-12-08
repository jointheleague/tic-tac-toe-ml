package com.ttt.ai;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.ttt.model.AI;
import com.ttt.model.Board;
import com.ttt.model.Tile;
import com.ttt.model.TilePosition;

public class TrialAI implements AI{

	JNeuralNetwork nn;
	Board board;
	
	public TrialAI(JNeuralNetwork nn){
		this.nn = nn;
	}
	
	public void setMaxDepth(JNeuralNetwork nn){
		this.nn = nn;
	}

	public JNeuralNetwork getMaxDepth(){
		return nn;
	}

	@Override
	public TilePosition getNextMove(Tile[][] tiles) {
		board = new Board(tiles);
		
		JLayer l = new JLayer(Board.BOARD_WIDTH * Board.BOARD_HEIGHT + 1);
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
				return new TilePosition(Tile.EMPTY, x, y);
			}
		}
		
		
		return null;
	}

}