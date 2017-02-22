package com.ttt.control;

public class TileOverrideException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "Tired to place a new tile on an already filled location";
	}

}
