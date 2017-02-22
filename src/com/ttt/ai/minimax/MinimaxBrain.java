package com.ttt.ai.minimax;

import com.ttt.model.Board;
import com.ttt.model.Brain;
import com.ttt.model.Tile;
import com.ttt.model.TilePosition;

public class MinimaxBrain implements Brain {
	private Tile tile;

	public MinimaxBrain(Tile tile) {
		this.tile = tile;
	}

	private static class Result {
		public Result(int i) {
			this.score = i;
		}

		public Result(TilePosition p, int i) {
			this.position = p;
			this.score = i;
		}

		private TilePosition position;
		private int score;
	}

	@Override
	public TilePosition getNextMove(Tile[][] tiles) {
		Result choice = minimax(tiles, 0, 9);
		System.out.println("choice: " + choice);
		return choice.position;
	}

	private Result minimax(Tile[][] tiles, int depth, int maxDepth) {
		boolean isMyTurn = depth % 2 == 0;
		Board tempBoard = new Board(tiles);
		if (tempBoard.checkWin(tile)) {
			//System.out.println("We have won in depth " + depth);
			return new Result(1);
		} else if (tempBoard.checkWin(tile == Tile.X ? Tile.O : Tile.X)) {
			//System.out.println("We have lost in depth " + depth);
			return new Result(-1);
		}
		//if (depth < maxDepth) {
			Result choice = new Result(isMyTurn ? -2 : 2);
			for (int i = 0; i < 9; i++) {
				int x = i % 3;
				int y = i / 3;
				if (tiles[x][y] == Tile.EMPTY) {
					tiles[x][y] = isMyTurn ? tile : (tile == Tile.X ? Tile.O : Tile.X);
					Result score = minimax(tiles, depth + 1, maxDepth);
					if (isMyTurn) {
						if (score.score > choice.score) {
							choice = new Result(new TilePosition(tile, x, y), score.score);
						}
					} else {
						if (score.score < choice.score) {
							choice = new Result(new TilePosition(tile, x, y), score.score);
						}
					}

					tiles[x][y] = Tile.EMPTY;
				}
			}
			return choice;
		//} else {
		//	System.out.println("Nobody has won in depth " + depth);
		//	return new Result(0);
		//}
	}
}
