package com.ttt.ai;

public class AiTester {
	static int tb[][] = {
			{-1,0,0,0,0},
			{-1,0,0,0,0},
			{-1,0,0,0,0},
			{-1,0,0,0,0},
			{-1,0,0,0,0},
			};

	public static void main(String[] args) {
		TTTSim sim = new TTTSim(3);
		sim.setBoard(tb);
		System.out.println(sim.isWinner(-1));
	}
}
