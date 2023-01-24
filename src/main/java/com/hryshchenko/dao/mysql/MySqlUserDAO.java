package com.hryshchenko.dao.mysql;

import com.hryshchenko.dao.DBUtil;
import com.hryshchenko.dao.UserDAO;
import com.hryshchenko.entity.Course;
import com.hryshchenko.entity.CourseStudent;
import com.hryshchenko.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * User entity DAO for MySQL database.
 * 
 * @author Hryshchenko
 *
 */
public class MySqlUserDAO implements UserDAO {
	public static final String GET_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
	public static final String GET_USER_BY_ID = "SELECT * FROM users WHERE id=?";
	public static final String GET_USERS = "SELECT * FROM users";
	public static final String GET_STUDENTS_OF_COURSE = "SELECT * FROM courses_students WHERE course_id = ?";
	public static final String UPDATE_MARKS = "UPDATE courses_students SET mark = ? WHERE course_id = ? AND student_id = ?";
	public static final String UPDATE_USER = "UPDATE users SET login = ?, password = ?, email = ?, status = ?, first_name = ?, last_name = ?, role_id = ? WHERE id = ?";
	public static final String REGISTER_USER = "INSERT INTO users VALUES(default, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static MySqlUserDAO instance;

	/**
	 * Initializiting the singleton.
	 * 
	 * @return Instance of singleton.
	 */
	public static synchronized MySqlUserDAO getInstance() {
		if (instance == null) {
			instance = new MySqlUserDAO();
		}
		return instance;
	}

	/**
	 * Private constructor.
	 */
	private MySqlUserDAO() {
	}

	/**
	 * Extracting user from Database
	 * 
	 * @param rs
	 * @return An instance of User
	 * @throws SQLException
	 */
	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setLogin(rs.getString("login"));
		user.setPassword(rs.getString("password"));
		user.setSalt(rs.getString("salt"));
		user.setEmail(rs.getString("email"));
		user.setStatus(rs.getString("status"));
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		user.setRoleId(rs.getInt("role_id"));
		return user;
	}

	/**
	 * Getting an instance of User by Login.
	 * 
	 * @param con
	 * @param login
	 * @return An instance of User.
	 * @throws SQLException
	 */
	@Override
	public User getUser(Connection con, String login) throws SQLException {
		User user = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(GET_USER_BY_LOGIN);
			ps.setString(1, login);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return user;
	}

	/**
	 * Getting an instance of User by id.
	 * 
	 * @param con
	 * @param id
	 * @return An instance of User.
	 * @throws SQLException
	 */
	@Override
	public User getUser(Connection con, int id) throws SQLException {
		User user = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(GET_USER_BY_ID);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return user;
	}

	/**
	 * Getting list of all users from DB.
	 *
	 * @param con
	 * @return List of all users.
	 * @throws SQLException
	 */
	@Override
	public List<User> getUsers(Connection con) throws SQLException {
		List<User> list = new ArrayList<>();
		User user = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(GET_USERS);
			rs = ps.executeQuery();
			while (rs.next()) {
				user = extractUser(rs);
				list.add(user);
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return list;
	}

	/**
	 * Extracting CourseStudent entity from database by given course.
	 * 
	 * @param con
	 * @param rs
	 * @param course
	 * @return Instance of CourseStudent.
	 * @throws SQLException
	 */
	private CourseStudent extractCourseStudentByCourse(Connection con, ResultSet rs, Course course)
			throws SQLException {
		CourseStudent cs = new CourseStudent();
		cs.setCourse(course);
		cs.setStudent(getUser(con, rs.getInt("student_id")));
		cs.setMark(rs.getInt("mark"));
		return cs;
	}

	/**
	 * Getting students list of given course.
	 * 
	 * @param con
	 * @param course
	 * @return List of CourseStudent entities.
	 * @throws SQLException
	 */
	@Override
	public List<CourseStudent> getStudentsOfCourse(Connection con, Course course) throws SQLException {
		List<CourseStudent> list = new ArrayList<>();
		CourseStudent courseStudent = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(GET_STUDENTS_OF_COURSE);
			ps.setInt(1, course.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				courseStudent = extractCourseStudentByCourse(con, rs, course);
				list.add(courseStudent);
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return list;
	}

	/**
	 * Updating user in DB.
	 * 
	 * @param con
	 * @param user
	 * @throws SQLException
	 */
	@Override
	public void updateUser(Connection con, User user) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(UPDATE_USER);
			ps.setString(1, user.getLogin());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getStatus());
			ps.setString(5, user.getFirstName());
			ps.setString(6, user.getLastName());
			ps.setInt(7, user.getRoleId());
			ps.setInt(8, user.getId());
			ps.executeUpdate();
		} finally {
			DBUtil.close(ps);
		}
	}

	/**
	 * Registering user and inserting its fields into DB.
	 * 
	 * @param con
	 * @param user
	 * @throws SQLException
	 */
	@Override
	public void register(Connection con, User user) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(REGISTER_USER);
			ps.setString(1, user.getLogin());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getSalt());
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getStatus());
			ps.setString(6, user.getFirstName());
			ps.setString(7, user.getLastName());
			ps.setInt(8, user.getRoleId());
			ps.executeUpdate();
		} finally {
			DBUtil.close(ps);
		}
	}

	/**
	 * Updating students marks in DB.
	 * 
	 * @param con
	 * @param courseId
	 * @param studentId
	 * @param mark
	 * @throws SQLException
	 */
	@Override
	public void updateStudentsOfCourse(Connection con, int courseId, int studentId, int mark) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(UPDATE_MARKS);
			ps.setInt(1, mark);
			ps.setInt(2, courseId);
			ps.setInt(3, studentId);
			ps.executeUpdate();
		} finally {
			DBUtil.close(ps);
		}
	}

}
