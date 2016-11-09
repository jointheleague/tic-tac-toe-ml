package com.ttt.control;

import com.ttt.model.Board;
import com.ttt.model.Player;
import com.ttt.model.TicTacToe;
import com.ttt.model.Tile;

public class GameController {
	
	private Player playerA;
	private Player playerB;
	private Board gameBoard;
	private int turnNum;
	
	public GameController(Player a, Player b, Board board){
		this.playerA = a;
		this.playerB = b;
		this.gameBoard = board;
		this.turnNum = 0;
		
		if(a.getTileType() == b.getTileType()){
			a.swapTileType();
		}
		
		if(gameBoard == null){
			System.err.println("Cannot Instantiate a Game On A Null Board Object!");
		}

	}
	
	public Player playGame() {
		Player winner = null;
		boolean playerTurn = true;
		boolean gameOver = false;
		while(!gameOver){
			turnNum++;
			if (turnNum > Board.BOARD_WIDTH * Board.BOARD_HEIGHT) {
				gameOver = true;
				break;
			}
			if(playerTurn){
				playerA.performTurn(gameBoard);
			}else{
				playerB.performTurn(gameBoard);
			}
			playerTurn = !playerTurn;
			
			if(gameBoard.checkWin(Tile.X)){
				gameOver = true;
				winner = playerA.getTileType() == Tile.X ? playerA : playerB;
			}else if (gameBoard.checkWin(Tile.O)){
				gameOver = true;
				winner = playerA.getTileType() == Tile.O ? playerA : playerB;
			}
		}
		return winner;
	}

}
