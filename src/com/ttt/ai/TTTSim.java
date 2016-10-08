package com.ttt.ai;

public class TTTSim {
	int[][] board;
	boolean playing = true;
	int moves;

	public TTTSim(int size) {
		moves = 0;
		board = new int[size][size];
		size = size - 1;
		System.out.println("Right size" + size);
		for (int i = 0; i <= size; i++) {
			for (int j = 0; j <= size; j++) {
				try {
					System.out.println(i + ", " + j);
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

	public void place(int type, int x, int y) {
		board[x][y] = type;
		moves++;
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

	public int score(int player) {
		int WL;
		if (isWinner(player))
			WL = 1;
		else
			WL = 0;

		int max = (2 ^ board.length) / 2;
		int score = WL * (max - moves);
		w
		return score;
	}
}