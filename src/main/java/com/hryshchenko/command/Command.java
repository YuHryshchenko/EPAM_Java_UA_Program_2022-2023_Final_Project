package com.hryshchenko.command;

import com.hryshchenko.dao.DBException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * The main interface of command pattern.
 * 
 * @author Hryshchenko
 *
 */
public interface Command extends Serializable {
	String execute(HttpServletRequest request, HttpServletResponse response) throws DBException, AppException;
}
