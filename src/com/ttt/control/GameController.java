package com.ttt.control;

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

		if (a.getTileType() == b.getTileType()) {
			a.swapTileType();
		}

		if (gameBoard == null) {
			System.err.println("Cannot Instantiate a Game On A Null Board Object!");
		}
	}
	
	private int attemptPerformTurn(Player toPlay){
		try{
			toPlay.performTurn(gameBoard);
		}catch (TileOverrideException e){
			System.err.println("Player  : " + toPlay.getLabel() + " Tried To Override An Already Written Tile!");
			return 1;
		}
		return 0;
	}

	public Player playGame() {
		boolean playerTurn = true;
		boolean gameEnded = false;
		while (!gameEnded) {
			if (playerTurn) {
				attemptPerformTurn(playerA);
			} else {
				attemptPerformTurn(playerB);
			}
			turnCount++;
			playerTurn = !playerTurn;
			gameEnded = ((gameBoard.checkWin(Tile.X) || (gameBoard.checkWin(Tile.O))
					|| turnCount >= (Board.BOARD_HEIGHT * Board.BOARD_WIDTH)));
		}
		if (turnCount >= (Board.BOARD_HEIGHT * Board.BOARD_WIDTH)) {
			if (gameBoard.checkWin(Tile.X)) {
				return playerA.getTileType() == Tile.X ? playerA : playerB;
			} else if (gameBoard.checkWin(Tile.O)) {
				return playerA.getTileType() == Tile.O ? playerA : playerB;
			} else {
				return null;
			}
		}
		if (gameBoard.checkWin(Tile.X)) {
			return playerA.getTileType() == Tile.X ? playerA : playerB;
		} else {
			return playerA.getTileType() == Tile.O ? playerA : playerB;
		}
	}
}
