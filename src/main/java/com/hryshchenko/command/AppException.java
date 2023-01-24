package com.hryshchenko.command;

/**
 * Own exception, thrown instead of catched ones.
 * 
 * @author Hryshchenko
 *
 */
public class AppException extends Exception {

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppException(String message) {
		super(message);
	}
}
