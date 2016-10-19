package com.ttt.model;

public class AIPlayer extends Player{
	
	private AINetwork brain;
	
	public void setNetwork(AINetwork net){
		this.brain = net;
	}

	public AIPlayer(String label) {
		super(label);
	}
	
	@Override
	public void performTurn(){
		brain.performTurn();
	}

}

