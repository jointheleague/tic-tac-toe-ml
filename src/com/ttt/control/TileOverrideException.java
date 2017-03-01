package com.ttt.control;

public class TileOverrideException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "Tried to place a new tile on an already filled location";
	}

}
