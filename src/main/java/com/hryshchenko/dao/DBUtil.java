package com.hryshchenko.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Util class for working with database.
 * 
 * @author Hryshchenko
 *
 */
public class DBUtil {
	private static final Logger log = LogManager.getLogger(DBUtil.class);

	/**
	 * Closing AutoCloseable objects
	 * 
	 * @param ac
	 */
	public static void close(AutoCloseable ac) {
		if (ac != null) {
			try {
				ac.close();
				log.debug("Resource is closed.");
			} catch (Exception e) {
				log.error("Failed to close resourse. " + e.getMessage());
			}
		}
	}

	/**
	 * Committing and closing a connection
	 * 
	 * @param con A connection which should be commited and closed
	 */
	public static void closeCommitConnection(Connection con) {
		if (con != null) {
			try {
				con.commit();
				con.close();
				log.debug("Connection is committed and returned to pool.");
			} catch (SQLException e) {
				log.error("Failed to commit and close connection. " + e.getMessage());
			}
		}
	}

	/**
	 * Rolling back and closing a connection
	 * 
	 * @param con A connection which should be rolled back and closed
	 */
	public static void closeRollbackConnection(Connection con) {
		if (con != null) {
			try {
				con.rollback();
				con.close();
				log.debug("Connection is rolled back and returned to pool.");
			} catch (SQLException e) {
				log.error("Failed to rollback and close connection. " + e.getMessage());
			}
		}
	}

	/**
	 * Private constructor
	 */
	private DBUtil() {
	}
}
