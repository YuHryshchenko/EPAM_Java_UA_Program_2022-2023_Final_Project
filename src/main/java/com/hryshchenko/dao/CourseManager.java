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
import java.util.NavigableSet;

/**
 * Class of higher level of database layer working with Course entity.
 * 
 * @author Hryshchenko
 *
 */
public class CourseManager {
	private static final Logger log = LogManager.getLogger(CourseManager.class);
	private DAOFactory daoFactory;
	private CourseDAO courseDAO;
	private static CourseManager instance;

	/**
	 * Initializing the singleton
	 * 
	 * @return An instance of singleton
	 */
	public static synchronized CourseManager getInstance() {
		if (instance == null) {
			instance = new CourseManager();
		}
		return instance;
	}

	/**
	 * Private constructor initializing DAOFactory and courseDAO fields.
	 * 
	 */
	private CourseManager() {
		daoFactory = DAOFactory.getDAOFactory(AdminConst.DB_TYPE);
		courseDAO = daoFactory.getCourseDAO();
	}

	/**
	 * Getting list of course themes.
	 * 
	 * @return List of themes.
	 * @throws DBException
	 */
	public NavigableSet<String> getThemes() throws DBException {
		log.debug("Starting getting list of all themes.");
		NavigableSet<String> set = null;
		Connection con = null;
		try {
			con = daoFactory.getConnection();
			set = courseDAO.getThemes(con);
			log.info("List of all themes is received.");
		} catch (SQLException e) {
			log.error("Failed to get list of all themes. " + e.getMessage());
			throw new DBException("course_manager.get_themes", e);
		} finally {
			DBUtil.close(con);
		}
		return set;
	}

	/**
	 * Getting course by id.
	 * 
	 * @param id
	 * @return an instance of Course.
	 * @throws DBException
	 */
	public Course getCourse(int id) throws DBException {
		log.debug("Starting getting course by id " + id + ".");
		Connection con = null;
		Course course = null;
		try {
			con = daoFactory.getConnection();
			course = courseDAO.getCourse(con, id);
			log.info("Found course by id " + id  + ".");
		} catch (SQLException e) {
			log.error("Failed to get course by id " + id + ". " + e.getMessage());
			throw new DBException("course_manager.get_course", e);
		} finally {
			DBUtil.close(con);
		}
		return course;
	}

	/**
	 * Getting list of all courses.
	 * 
	 * @return List of all courses.
	 * @throws DBException
	 */
	public List<Course> getCourses() throws DBException {
		log.debug("Starting getting list of all courses.");
		List<Course> list = null;
		Connection con = null;
		try {
			con = daoFactory.getConnection();
			list = courseDAO.getCourses(con);
			log.info("Received list of all courses.");
		} catch (SQLException e) {
			log.error("Failed to get list of all courses. " + e.getMessage());
			throw new DBException("course_manager.get_courses", e);
		} finally {
			DBUtil.close(con);
		}
		return list;
	}

	/**
	 * Getting list of courses by teacher's id.
	 * 
	 * @param teacherId
	 * @return List of courses of given teacher.
	 * @throws DBException
	 */
	public List<Course> getCoursesByTeacher(int teacherId) throws DBException {
		log.debug("Starting getting courses by teacher id " + teacherId + ".");
		List<Course> list = null;
		Connection con = null;
		try {
			con = daoFactory.getConnection();
			list = courseDAO.getCoursesByTeacher(con, teacherId);
			log.info("Found all courses by teacher id " + teacherId  + ".");
		} catch (SQLException e) {
			log.error("Failed to get all courses by teacher id " + teacherId + ". " + e.getMessage());
			throw new DBException("course_manager.get_teacher_courses", e);
		} finally {
			DBUtil.close(con);
		}
		return list;
	}

	/**
	 * Getting list of given student's courses.
	 * 
	 * @param student
	 * @return List of CourseStudent entities.
	 * @throws DBException
	 */
	public List<CourseStudent> getCoursesOfStudent(User student) throws DBException {
		log.debug("Starting getting list of courses of a student " + student + ".");
		List<CourseStudent> list = null;
		Connection con = null;
		try {
			con = daoFactory.getConnection();
			list = courseDAO.getCoursesOfStudent(con, student);
			log.info("Received list of all courses of student " + student + ".");
		} catch (SQLException e) {
			log.error("Failed to get list of courses of student " + student + ". " + e.getMessage());
			throw new DBException("course_manager.get_student_courses", e);
		} finally {
			DBUtil.close(con);
		}
		return list;
	}

	/**
	 * Subscribing to course applying transaction.
	 * 
	 * @param courseId
	 * @param studentId
	 * @throws DBException
	 */
	public void subscribeToCourse(int courseId, int studentId) throws DBException {
		log.debug("Starting subscribing to course" + courseId + "by student " + studentId + ".");
		Connection con = null;
		try {
			con = daoFactory.getConnection();
			courseDAO.subscribeToCourse(con, courseId, studentId);
			log.info("Student " + studentId + "has subscribed to course " + courseId + ".");
			DBUtil.closeCommitConnection(con);
		} catch (SQLException e) {
			log.error("Student " + studentId + " failed to subscribe to course " + courseId + ". " + e.getMessage());
			DBUtil.closeRollbackConnection(con);
			throw new DBException("course_manager.subscribe", e);
		}
	}

	/**
	 * Inserting a new course into DB applying transaction.
	 * 
	 * @param course
	 * @throws DBException
	 */
	public void createCourse(Course course) throws DBException {
		log.debug("Starting creating a new course" + course + ".");
		Connection con = null;
		try {
			con = daoFactory.getConnection();
			courseDAO.createCourse(con, course);
			log.info("Created new course " + course + ".");
			DBUtil.closeCommitConnection(con);
		} catch (SQLException e) {
			log.error("Failed to create new course " + course + ". " + e.getMessage());
			DBUtil.closeRollbackConnection(con);
			throw new DBException("course_manager.create", e);
		}
	}

	/**
	 * Removing given course from DB applying transaction.
	 * 
	 * @param id
	 * @throws DBException
	 */
	public void removeCourse(int id) throws DBException {
		log.debug("Starting removing course " + id + ".");
		Connection con = null;
		try {
			con = daoFactory.getConnection();
			courseDAO.removeCourse(con, id);
			log.info("Removed course " + id + ".");
			DBUtil.closeCommitConnection(con);
		} catch (SQLException e) {
			log.error("Failed to remove course " + id + ". " + e.getMessage());
			DBUtil.closeRollbackConnection(con);
			throw new DBException("course_manager.remove", e);
		}
	}

	/**
	 * Updating course in DB applying transaction.
	 * 
	 * @param course
	 * @throws DBException
	 */
	public void updateCourse(Course course) throws DBException {
		log.debug("Starting editing course" + course + ".");
		Connection con = null;
		try {
			con = daoFactory.getConnection();
			courseDAO.updateCourse(con, course);
			log.info("Updated course " + course + ".");
			DBUtil.closeCommitConnection(con);
		} catch (SQLException e) {
			log.error("Failed to update course " + course + ". " + e.getMessage());
			DBUtil.closeRollbackConnection(con);
			throw new DBException("course_manager.edit", e);
		}
	}

}
