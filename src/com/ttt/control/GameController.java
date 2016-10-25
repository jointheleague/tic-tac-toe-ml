package com.ttt.control;

import com.ttt.model.Board;
import com.ttt.model.Player;
import com.ttt.model.TicTacToe;
import com.ttt.model.Tile;

public class GameController {
	
	private Player playerA;
	private Player playerB;
	private Board gameBoard;
	
	public GameController(Player a, Player b, Board board){
		this.playerA = a;
		this.playerB = b;
		this.gameBoard = board;
		
		if(a.getTileType() == b.getTileType()){
			a.swapTileType();
		}
		
		if(gameBoard == null){
			System.err.println("Cannot Instantiate a Game On A Null Board Object!");
		}

	}
	
	public Player playGame(){
		boolean playerTurn = true;
		boolean playerWon = false;
		while(!playerWon){
			if(playerTurn){
				playerA.performTurn(gameBoard);
			}else{
				playerB.performTurn(gameBoard);
			}
			playerTurn = !playerTurn;
			playerWon = ((gameBoard.checkWin(Tile.X) || (gameBoard.checkWin(Tile.O))));
		}
		if(gameBoard.checkWin(Tile.X)){
			return playerA.getTileType() == Tile.X ? playerA : playerB;
		}else{
			return playerA.getTileType() == Tile.O ? playerA : playerB;
		}
	}

}
