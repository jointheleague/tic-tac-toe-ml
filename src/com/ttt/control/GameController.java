package com.ttt.control;

import com.ttt.model.Board;
import com.ttt.model.Player;
import com.ttt.model.Tile;

public class GameController {
	private Player playerA;
	private Player playerB;
	private Board gameBoard;
	private boolean isXTurn = true;
	private long wait = 0;

	public GameController(Player a, Player b, Board board) {
		this.playerA = a;
		this.playerB = b;
		this.gameBoard = board;

		if (gameBoard == null) {
			System.err.println("Cannot Instantiate a Game On A Null Board Object!");
		}

	}

	public Player playGame() {
		while (true) {
			performTurn();

			if (gameBoard.checkWin(Tile.X) || (gameBoard.checkWin(Tile.O))) {
				return gameBoard.checkWin(Tile.X) ? playerA : playerB;
			} else if (gameBoard.isTie()) {
				return null;
			}
		}
	}

	public void performTurn() {
		if (isXTurn) {
			playerA.performTurn(gameBoard);
		} else {
			playerB.performTurn(gameBoard);
		}
		isXTurn = !isXTurn;
		if (wait > 0) {
			try {
				Thread.sleep(wait);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public long getWait() {
		return wait;
	}

	public void setWait(long wait) {
		this.wait = wait;
	}
}
