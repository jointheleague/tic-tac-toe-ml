package com.ttt.ai.hal;

import com.ttt.ai.minimax.Minimax;
import com.ttt.model.Tile;

public class TTTSim {
	int[][] board;
	boolean playing = true;
	int moves;
	public static boolean Logging = false;

	public TTTSim(int size) {
		moves = 0;
		board = new int[size][size];
		size = size - 1;
		for (int i = 0; i <= size; i++) {
			for (int j = 0; j <= size; j++) {
				try {
					board[i][j] = 0;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] b) {
		board = b;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

	public void printBoard() {
		if (Logging) {
			System.out.println("");
			System.out.println("Moves:" + moves);
			for (int i = 0; i < board.length; i++) {
				System.out.println("");
				for (int j = 0; j < board.length; j++) {
					if (board[i][j] == -1)
						System.out.print(2);
					else
						System.out.print(board[i][j]);

				}
			}
			System.out.println("");
		}
	}

	public boolean place(int type, int x, int y) {
		if (board[x][y] != 0)
			return false;
		board[x][y] = type;
		moves++;
		return true;
	}

	public boolean testForWin() {
		if (isWinner(-1) || isWinner(1) || isTie()) {
			this.playing = false;
			return true;
		}
		return false;
	}

	public boolean isTie() {
		boolean tie = true;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 0) {
					tie = false;
				}
			}
		}
		if (tie) {
			return !isWinner(-1) && !isWinner(1);
		}
		return false;
	}

	public boolean isWinner(int player) {
		for (int i = 0; i < board.length; i++) {
			boolean verticalWin = true, horizontalWin = true;
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] != player)
					horizontalWin = false;
				if (board[j][i] != player)
					verticalWin = false;
				if (!(horizontalWin || verticalWin))
					break;
			}
			if (horizontalWin || verticalWin) {
				playing = false;
				return true;
			}
		}
		boolean diagonalWinOne = true, diagonalWinTwo = true;
		for (int n = 0; n < board.length; n++) {
			diagonalWinOne = true;
			diagonalWinTwo = true;
			int row = board.length - 1 - n;
			if (board[n][n] != player)
				diagonalWinOne = false;
			if (board[row][n] != player)
				diagonalWinTwo = false;
			if (!(diagonalWinOne || diagonalWinTwo))
				break;
		}
		if (diagonalWinOne || diagonalWinTwo) {
			playing = false;
			return true;
		} else
			return false;
	}

	public boolean playing() {
		return playing;
	}

	public double score(int player) {
		int max = (board.length * board.length) / 2;
		int turns = moves / 2;
		double score;
		score = max - turns;
		// System.out.println("Score: " + score);
		return score;
	}

	private static final Minimax minimax = new Minimax(2, Tile.X);

	public void minimaxMove(int player) {
		Tile[][] convert = new Tile[HALMainController.size][HALMainController.size];
		for (int i = 0; i < HALMainController.size; i++) {
			for (int j = 0; j < HALMainController.size; j++) {
				convert[i][j] = board[i][j] == 1 ? Tile.X : (board[i][j] == -1 ? Tile.O : Tile.EMPTY);
			}
		}
		try {
			minimax.getNextMove(convert);
			board[minimax.computersMove.x][minimax.computersMove.y] = 1;
			moves++;
		} catch (Exception e) {
			System.err.println("Ruoya.");
		}
	}
}