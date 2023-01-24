package com.hryshchenko.dao.mysql;

import com.hryshchenko.dao.CourseDAO;
import com.hryshchenko.dao.DAOFactory;
import com.hryshchenko.dao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * DAO class for working with MySQL database.
 * 
 * @author Hryshchenko
 *
 */
public class MySqlDAOFactory extends DAOFactory {
	private static final Logger log = LogManager.getLogger(MySqlDAOFactory.class);
	private static MySqlDAOFactory instance;
	private static DataSource ds;

	/**
	 * Initializing the singleton.
	 * 
	 * @return Instance of the singleton.
	 */
	public static synchronized MySqlDAOFactory getInstance() {
		if (instance == null) {
			instance = new MySqlDAOFactory();
		}
		return instance;
	}

	/**
	 * Getting instance of MySqlUserDAO.
	 *
	 * @return Instance of MySqlUserDAO.
	 */
	@Override
	public UserDAO getUserDAO() {
		return MySqlUserDAO.getInstance();
	}

	/**
	 * Getting instance of MySqlCourseDAO.
	 *
	 * @return Instance of MySqlCourseDAO.
	 */
	@Override
	public CourseDAO getCourseDAO() {
		return MySqlCourseDAO.getInstance();
	}

	/**
	 * Getting connection from the pool.
	 * 
	 * @return Connection from the pool.
	 */
	@Override
	public Connection getConnection() throws SQLException {
		log.debug("Getting connection from pool");
		return ds.getConnection();
	}

	/**
	 * Private constructor initializing DataSource field.
	 */
	private MySqlDAOFactory() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/FacultativeDB");
		} catch (NamingException e) {
			log.fatal("Failure while getting connection pool. " + e.getMessage());
			throw new IllegalStateException("Database is unavailable.");
		}
	}
}
