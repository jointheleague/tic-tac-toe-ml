package com.ttt.control;

import com.ttt.model.Board;
import com.ttt.model.Player;
import com.ttt.model.TicTacToe;
import com.ttt.model.Tile;

public class GameController {
	
	private Player playerA;
	private Player playerB;
	private Board gameBoard;
	private int turnCount = 0;
	
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
		
		TicTacToe.setBoard(gameBoard);

	}
	
	public Player playGame(){
		boolean playerTurn = true;
		boolean playerWon = false;
		while(!playerWon){
			if(playerTurn){
				turnCount++;
				playerA.performTurn(gameBoard);
			}else{
				turnCount++;
				playerB.performTurn(gameBoard);
			}
			playerTurn = !playerTurn;
			playerWon = ((gameBoard.checkWin(Tile.X) || (gameBoard.checkWin(Tile.O)) || turnCount >= (gameBoard.BOARD_HEIGHT * gameBoard.BOARD_WIDTH)));
		}
		if(turnCount >= (gameBoard.BOARD_HEIGHT * gameBoard.BOARD_WIDTH)){
			return new Player("Tie");
		}
		if(gameBoard.checkWin(Tile.X)){
			return playerA.getTileType() == Tile.X ? playerA : playerB;
		}else{
			return playerA.getTileType() == Tile.O ? playerA : playerB;
		}
	}

}
