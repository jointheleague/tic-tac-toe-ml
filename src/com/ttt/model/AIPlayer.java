package com.ttt.model;

public class AIPlayer extends Player {

	private AI brain;

	public void setNetwork(AI net) {
		this.brain = net;
	}

	public AIPlayer(String label) {
		super(label);
	}

	@Override
	public void performTurn(Board b) {
		TilePosition pos = brain.getNextMove(b);
		b.setTile(pos.getX(), pos.getY(), pos.getTile());
	}

}
