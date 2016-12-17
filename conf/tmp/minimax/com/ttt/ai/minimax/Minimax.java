package com.ttt.ai.minimax;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.ttt.model.Board;
import com.ttt.model.Brain;
import com.ttt.model.Tile;
import com.ttt.model.TilePosition;

public class Minimax implements Brain {
	private Board board;
	private Tile tile;
	private int maxDepth;

	public Minimax(int maxDepth, Tile tile) {
		this.maxDepth = maxDepth;
		this.tile = tile;
	}

	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	@Override
	public TilePosition getNextMove(Tile[][] tiles) {
		board = new Board(tiles);
		minimax(0, 1);
		return new TilePosition(tile, computersMove.x, computersMove.y);
	}

	List<Point> availablePoints;
	Point computersMove;

	public List<Point> getAvailableStates() {
		availablePoints = new ArrayList<>();
		for (int i = 0; i < Board.BOARD_WIDTH; ++i) {
			for (int j = 0; j < Board.BOARD_HEIGHT; ++j) {
				if (board.getTile(i, j) == Tile.EMPTY) {
					availablePoints.add(new Point(i, j));
				}
			}
		}
		return availablePoints;
	}

	public int minimax(int depth, int turn) {
		if (board.checkWin(Tile.X))
			return +1;
		if (board.checkWin(Tile.O))
			return -1;

		if (depth > maxDepth) {
			return 0;
		}

		List<Point> pointsAvailable = getAvailableStates();
		if (pointsAvailable.isEmpty())
			return 0;

		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

		for (int i = 0; i < pointsAvailable.size(); ++i) {
			Point point = pointsAvailable.get(i);
			if (turn == 1) { // X's Turn
				board.setTile(point.x, point.y, Tile.X);
				int currentScore = minimax(depth + 1, 2);
				max = Math.max(currentScore, max);

				if (depth == 0)
					if (currentScore >= 0) {
						if (depth == 0)
							computersMove = point;
					}
				if (currentScore == 1) {
					board.setTile(point.x, point.y, Tile.EMPTY);
					break;
				}
				if (i == pointsAvailable.size() - 1 && max < 0) {
					if (depth == 0)
						computersMove = point;
				}
			} else if (turn == 2) { // O's Turn
				board.setTile(point.x, point.y, Tile.O);
				int currentScore = minimax(depth + 1, 1);
				min = Math.min(currentScore, min);
				if (min == -1) {
					board.setTile(point.x, point.y, Tile.EMPTY);
					break;
				}
			}
			board.setTile(point.x, point.y, Tile.EMPTY); // Reset this point
		}
		return turn == 1 ? max : min;
	}

}