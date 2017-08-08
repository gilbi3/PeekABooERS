package com.revature.exceptions;

public class ImageNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -6573307333524845568L;
	
	public ImageNotFoundException(String s) {
		super(s);
	}

}
