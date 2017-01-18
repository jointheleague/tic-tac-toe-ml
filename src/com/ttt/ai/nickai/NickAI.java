package com.ttt.ai.nickai;

import java.util.ArrayList;

import com.ttt.model.Brain;
import com.ttt.model.Tile;
import com.ttt.model.TilePosition;

import me.najclark.gll.nn.Layer;
import me.najclark.gll.nn.NeuralNetwork;
import me.najclark.gll.nn.Neuron;

public class NickAI implements Brain {

	NeuralNetwork brain;

	public NickAI(NeuralNetwork brain) {
		this.brain = brain;
	}

	@Override
	public TilePosition getNextMove(Tile[][] tiles) {
		
		Neuron[] inputs = new Neuron[tiles.length * tiles[0].length];
		for(int x = 0; x < tiles.length; x++){
			for(int y = 0; y < tiles[0].length; y++){
				inputs[x+y*tiles.length] =  new Neuron(tiles[x][y].getValue());
			}
		}
		
		brain.setInputs(inputs);
		brain.flush();
		
		Layer out = brain.getOutputs();
		ArrayList<Integer> choices = out.getHighest2Lowest();
		for(int index : choices){
			int x = index/tiles.length;
			int y = index%tiles[0].length;
			if(tiles[x][y] == Tile.EMPTY){
				return new TilePosition(x, y);
			}
		}
		
		return null;
	}

}
