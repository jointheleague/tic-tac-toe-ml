package com.ttt.control;

import com.ttt.TicTacToe;
import com.ttt.model.Board;
import com.ttt.model.Player;
import com.ttt.model.Tile;

public class GameController {

	private Player playerA;
	private Player playerB;
	private Board gameBoard;
	private int turnCount = 0;

	public GameController(Player a, Player b, Board board) {
		this.playerA = a;
		this.playerB = b;
		this.gameBoard = board;
<<<<<<< HEAD
		
		if (a.getTileType() == b.getTileType()) {
			a.swapTileType();
		}
		if (gameBoard == null) {
			System.err.println("Cannot Instantiate a Game On A Null Board Object!");
		}
=======

		if (a.getTileType() == b.getTileType()) {
			a.swapTileType();
		}

		if (gameBoard == null) {
			System.err.println("Cannot Instantiate a Game On A Null Board Object!");
		}

>>>>>>> master
		TicTacToe.setBoard(gameBoard);
	}
<<<<<<< HEAD
	
=======

>>>>>>> master
	public Player playGame() {
		boolean playerTurn = true;
		boolean playerWon = false;
		while (!playerWon) {
			if (playerTurn) {
				turnCount++;
				playerA.performTurn(gameBoard);
			} else {
				turnCount++;
				playerB.performTurn(gameBoard);
			}
			playerTurn = !playerTurn;
			playerWon = ((gameBoard.checkWin(Tile.X) || (gameBoard.checkWin(Tile.O))
<<<<<<< HEAD
					|| turnCount >= (gameBoard.BOARD_HEIGHT * gameBoard.BOARD_WIDTH)));
		}
		if (gameBoard.checkWin(Tile.X)) {
			return playerA.getTileType() == Tile.X ? playerA : playerB;
		} else if (gameBoard.checkWin(Tile.O)) {
=======
					|| turnCount >= (Board.BOARD_HEIGHT * Board.BOARD_WIDTH)));
		}
		if (turnCount >= (Board.BOARD_HEIGHT * Board.BOARD_WIDTH)) {
			return null;
		}
		if (gameBoard.checkWin(Tile.X)) {
			return playerA.getTileType() == Tile.X ? playerA : playerB;
		} else {
>>>>>>> master
			return playerA.getTileType() == Tile.O ? playerA : playerB;
		}
		return new Player("Draw");
	}
}
