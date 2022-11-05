package com.cognixia.jump.exception;

public class NotEnoughStockException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotEnoughStockException(String msg) {
		super(msg);
	}

	public NotEnoughStockException(String game, int qty) {
		super("Not enough stock of " + game + " available to sell " + qty);
	}

}
