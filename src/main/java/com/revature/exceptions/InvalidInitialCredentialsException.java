package com.revature.exceptions;

public class InvalidInitialCredentialsException extends RuntimeException{

	private static final long serialVersionUID = -6573307333524845568L;
	
	public InvalidInitialCredentialsException(String s) {
		super(s);
	}

}
