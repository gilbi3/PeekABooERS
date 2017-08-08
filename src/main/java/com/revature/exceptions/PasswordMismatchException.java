package com.revature.exceptions;

public class PasswordMismatchException extends RuntimeException{

	private static final long serialVersionUID = -6573307333524845568L;
	
	public PasswordMismatchException(String s) {
		super(s);
	}

}
