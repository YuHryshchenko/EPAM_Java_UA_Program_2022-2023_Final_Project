package com.hryshchenko.dao;

/**
 * Own exception, thrown instead of catched ones.
 * 
 * @author Hryshchenko
 *
 */
public class DBException extends Exception {

	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBException(String message) {
		super(message);
	}
}
