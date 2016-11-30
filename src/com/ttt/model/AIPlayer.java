package com.ttt.model;

public class AIPlayer extends Player {
	private AINetwork brain;

	public void setNetwork(AINetwork net) {
		this.brain = net;
	}

	public AIPlayer(String label, AINetwork brain) {
		super(label);
		this.brain = brain;
	}

	@Override
	public void performTurn(Board b) {
		brain.performTurn(b);
	}
}
