package com.revature.exceptions;

public class NoSessionFoundException extends RuntimeException{

	private static final long serialVersionUID = -1278296090478828745L;
	
	public NoSessionFoundException(String s) {
		super(s);
	}

}
