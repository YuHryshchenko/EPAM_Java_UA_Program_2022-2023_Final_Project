package com.hryshchenko.dao;

import com.hryshchenko.constant.AdminConst;
import com.hryshchenko.entity.Course;
import com.hryshchenko.entity.CourseStudent;
import com.hryshchenko.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Class of higher level of database layer working with User entity.
 * 
 * @author Hryshchenko
 *
 */
public class UserManager {
	private static final Logger log = LogManager.getLogger(UserManager.class);
	private DAOFactory daoFactory;
	private UserDAO userDAO;
	private static UserManager instance;

	/**
	 * Initializing the singleton.
	 * 
	 * @return An instance of singleton.
	 */
	public static synchronized UserManager getInstance() {
		if (instance == null) {
			instance = new UserManager();
		}
		return instance;
	}

	/**
	 * Private constructor initializing DAOFactory and userDAO fields.
	 * 
	 */
	private UserManager() {
		daoFactory = DAOFactory.getDAOFactory(AdminConst.DB_TYPE);
		userDAO = daoFactory.getUserDAO();
	}

	/**
	 * Getting user by login.
	 * 
	 * @param login
	 * @return an instance of User.
	 * @throws DBException
	 */
	public User getUserByLogin(String login) throws DBException {
		log.debug("Starting getting user by login " + login + ".");
		Connection con = null;
		User user = null;
		try {
			con = daoFactory.getConnection();
			user = userDAO.getUserByLogin(con, login);
			log.info("Found user by login: " + login + ".");
		} catch (SQLException e) {
			log.error("Failed to get user by login: " + login + ". " + e.getMessage());
			throw new DBException("user_manager.get_login", e);
		} finally {
			DBUtil.close(con);
		}
		return user;
	}

	/**
	 * Getting user by id.
	 * 
	 * @param id
	 * @return an instance of User.
	 * @throws DBException
	 */
	public User getUserById(int id) throws DBException {
		log.debug("Starting getting user by id " + id + ".");
		Connection con = null;
		User user = null;
		try {
			con = daoFactory.getConnection();
			user = userDAO.getUserById(con, id);
			log.info("Found user by id: " + id + ".");
		} catch (SQLException e) {
			log.error("Failed to get user by id: " + id + ". " + e.getMessage());
			throw new DBException("user_manager.get_user", e);
		} finally {
			DBUtil.close(con);
		}
		return user;
	}

	/**
	 * Getting all users.
	 * 
	 * @return List of all users.
	 * @throws DBException
	 */
	public List<User> getUsers() throws DBException {
		log.debug("Starting getting all users.");
		List<User> list = null;
		Connection con = null;
		try {
			con = daoFactory.getConnection();
			list = userDAO.getUsers(con);
			log.info("List of all users is received.");
		} catch (SQLException e) {
			log.error("Failed to get list of all users. " + e.getMessage());
			throw new DBException("user_manager.get_users", e);
		} finally {
			DBUtil.close(con);
		}
		return list;
	}

	/**
	 * Getting list of given course's students.
	 * 
	 * @param course
	 * @return List of CourseStudent entities.
	 * @throws DBException
	 */
	public List<CourseStudent> getStudentsOfCourse(Course course) throws DBException {
		log.debug("Starting getting students of course " + course + ".");
		List<CourseStudent> list = null;
		Connection con = null;
		try {
			con = daoFactory.getConnection();
			list = userDAO.getStudentsOfCourse(con, course);
			log.info("Received list of course " + course + ".");
		} catch (SQLException e) {
			log.error("Failed to get list of course " + course + ". " + e.getMessage());
			throw new DBException("user_manager.get_students", e);
		} finally {
			DBUtil.close(con);
		}
		return list;
	}

	/**
	 * Updating marks in DB applying transaction.
	 * 
	 * @param courseId
	 * @param studentId
	 * @param mark
	 * @throws DBException
	 */
	public void updateStudentsOfCourse(int courseId, int studentId, int mark) throws DBException {
		log.debug("Starting updating marks.");
		Connection con = null;
		try {
			con = daoFactory.getConnection();
			userDAO.updateStudentsOfCourse(con, courseId, studentId, mark);
			log.info("Marks are updated successfully.");
			DBUtil.closeCommitConnection(con);
		} catch (SQLException e) {
			log.error("Failed to update marks." + e.getMessage());
			DBUtil.closeRollbackConnection(con);
			throw new DBException("user_manager.edit_marks", e);
		}
	}

	/**
	 * Registering a user (applying transaction).
	 * 
	 * @param user
	 * @throws DBException
	 */
	public void register(User user) throws DBException {
		log.debug("Starting registration of a new user.");
		Connection con = null;
		try {
			con = daoFactory.getConnection();
			userDAO.register(con, user);
			log.info("Registered user " + user + ".");
			DBUtil.closeCommitConnection(con);
		} catch (SQLException e) {
			log.error("Failed to register new user. " + e.getMessage());
			DBUtil.closeRollbackConnection(con);
			throw new DBException("user_manager.register", e);
		}
	}

	/**
	 * Updating user applying transaction.
	 * 
	 * @param user
	 * @throws DBException
	 */
	public void updateUser(User user) throws DBException {
		log.debug("Starting updating user " + user + ".");
		Connection con = null;
		try {
			con = daoFactory.getConnection();
			userDAO.updateUser(con, user);
			log.info("Updated user " + user + ".");
			DBUtil.closeCommitConnection(con);
		} catch (SQLException e) {
			log.error("Failed to update user " + user + ". " + e.getMessage());
			DBUtil.closeRollbackConnection(con);
			throw new DBException("user_manager.edit_user", e);
		}
	}

}
