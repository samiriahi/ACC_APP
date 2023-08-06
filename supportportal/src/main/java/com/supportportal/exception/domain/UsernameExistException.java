package com.supportportal.exception.domain;

public class UsernameExistException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsernameExistException(String message) {
        super(message);
    }
}
