package com.hryshchenko.dao.mysql;

import com.hryshchenko.dao.CourseDAO;
import com.hryshchenko.dao.DBUtil;
import com.hryshchenko.entity.Course;
import com.hryshchenko.entity.CourseStudent;
import com.hryshchenko.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * Course entity DAO for MySQL database.
 * 
 * @author Hryshchenko
 *
 */
public class MySqlCourseDAO implements CourseDAO {
	public static final String GET_THEMES = "SELECT theme FROM courses";
	public static final String GET_COURSE = "SELECT * FROM courses WHERE id = ?";
	public static final String GET_COURSES = "SELECT * FROM courses";
	public static final String GET_COURSES_OF_TEACHER = "SELECT * FROM courses WHERE teacher_id = ?";
	public static final String GET_COURSES_OF_STUDENT = "SELECT * FROM courses_students WHERE student_id = ?";
	public static final String SUBSCRIBE_TO_COURSE = "INSERT INTO courses_students VALUES(?, ?, default)";
	public static final String SUBSCRIBE_UPDATE_COURSE = "UPDATE courses SET students = students+1 WHERE id = ?";
	public static final String REMOVE_COURSE = "DELETE FROM courses WHERE id = ?";
	public static final String UPDATE_COURSE = "UPDATE courses SET title = ?, theme = ?, teacher_id = ?, start_date = ?, finish_date = ?, length = ? WHERE id = ?";
	public static final String CREATE_COURSE = "INSERT INTO courses VALUES(default, ?, ?, ?, ?, ?, ?, default)";

	private static MySqlCourseDAO instance;

	/**
	 * Initializing the singleton.
	 * 
	 * @return Instance of singleton.
	 */
	public static synchronized MySqlCourseDAO getInstance() {
		if (instance == null) {
			instance = new MySqlCourseDAO();
		}
		return instance;
	}

	/**
	 * Private constructor.
	 */
	private MySqlCourseDAO() {

	}

	/**
	 * Getting list of available course themes.
	 * 
	 * @param con
	 * @return List of themes.
	 * @throws SQLException
	 */
	@Override
	public NavigableSet<String> getThemes(Connection con) throws SQLException {
		NavigableSet<String> set = new TreeSet<>();
		String theme = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(GET_THEMES);
			rs = ps.executeQuery();
			while (rs.next()) {
				theme = rs.getString("theme");
				set.add(theme);
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return set;
	}

	/**
	 * Extracting course from Database
	 * 
	 * @param rs
	 * @return An instance of Course
	 * @throws SQLException
	 */
	private Course extractCourse(ResultSet rs) throws SQLException {
		Course course = new Course();
		course.setId(rs.getInt("id"));
		course.setTitle(rs.getString("title"));
		course.setTheme(rs.getString("theme"));
		course.setTeacherId(rs.getInt("teacher_id"));
		course.setStartDate(rs.getDate("start_date"));
		course.setFinishDate(rs.getDate("finish_date"));
		course.setLength(rs.getInt("length"));
		course.setStudents(rs.getInt("students"));
		return course;
	}

	/**
	 * Getting an instance of Course.
	 * 
	 * @param con
	 * @param  id
	 * @return Instance of Course
	 * @throws SQLException
	 */
	@Override
	public Course getCourse(Connection con, int id) throws SQLException {
		Course course = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(GET_COURSE);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				course = extractCourse(rs);
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return course;
	}

	/**
	 * Getting list of all courses.
	 * 
	 * @param con
	 * @return List of courses
	 * @throws SQLException
	 */
	@Override
	public List<Course> getCourses(Connection con) throws SQLException {
		List<Course> list = new ArrayList<>();
		Course course = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(GET_COURSES);
			rs = ps.executeQuery();
			while (rs.next()) {
				course = extractCourse(rs);
				list.add(course);
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return list;
	}

	/**
	 * Getting list of courses of given teacher.
	 * 
	 * @param con
	 * @param teacherId
	 * @return List of courses of a teacher.
	 * @throws SQLException
	 */
	@Override
	public List<Course> getCoursesByTeacher(Connection con, int teacherId) throws SQLException {
		List<Course> list = new ArrayList<>();
		Course course = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(GET_COURSES_OF_TEACHER);
			ps.setInt(1, teacherId);
			rs = ps.executeQuery();
			while (rs.next()) {
				course = extractCourse(rs);
				list.add(course);
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return list;
	}

	/**
	 * Extracting CourseStudent entity from database by given student.
	 * 
	 * @param con
	 * @param rs
	 * @param student
	 * @return Instance of CourseStudent.
	 * @throws SQLException
	 */
	private CourseStudent extractCourseStudentByStudent(Connection con, ResultSet rs, User student)
			throws SQLException {
		CourseStudent cs = new CourseStudent();
		cs.setCourse(getCourse(con, rs.getInt("course_id")));
		cs.setStudent(student);
		cs.setMark(rs.getInt("mark"));
		return cs;
	}

	/**
	 * Getting list of given student's courses from DB.
	 * 
	 * @param con
	 * @param student
	 * @return List of CourseStudent entities.
	 * @throws SQLException
	 */
	@Override
	public List<CourseStudent> getCoursesOfStudent(Connection con, User student) throws SQLException {
		List<CourseStudent> list = new ArrayList<>();
		CourseStudent courseStudent = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(GET_COURSES_OF_STUDENT);
			ps.setInt(1, student.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				courseStudent = extractCourseStudentByStudent(con, rs, student);
				list.add(courseStudent);
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return list;
	}

	/**
	 * Subscribing student to a course and updating info in table courses.
	 * 
	 * @param con
	 * @param courseId
	 * @param studentId
	 * @throws SQLException
	 */
	@Override
	public void subscribeToCourse(Connection con, int courseId, int studentId) throws SQLException {
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		try {
			ps1 = con.prepareStatement(SUBSCRIBE_TO_COURSE);
			ps1.setInt(1, courseId);
			ps1.setInt(2, studentId);
			ps1.executeUpdate();
			ps2 = con.prepareStatement(SUBSCRIBE_UPDATE_COURSE);
			ps2.setInt(1, courseId);
			ps2.executeUpdate();
		} finally {
			DBUtil.close(ps1);
			DBUtil.close(ps2);
		}
	}

	/**
	 * Removing course from DB by id.
	 * 
	 * @param con
	 * @param id
	 * @throws SQLException
	 */
	@Override
	public void removeCourse(Connection con, int id) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(REMOVE_COURSE);
			ps.setInt(1, id);
			ps.executeUpdate();
		} finally {
			DBUtil.close(ps);
		}
	}

	/**
	 * Update course info in DB.
	 * 
	 * @param con
	 * @param course
	 * @throws SQLException
	 */
	@Override
	public void updateCourse(Connection con, Course course) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(UPDATE_COURSE);
			ps.setString(1, course.getTitle());
			ps.setString(2, course.getTheme());
			ps.setInt(3, course.getTeacherId());
			ps.setDate(4, new java.sql.Date(course.getStartDate().getTime()));
			ps.setDate(5, new java.sql.Date(course.getFinishDate().getTime()));
			ps.setInt(6, course.getLength());
			ps.setInt(7, course.getId());
			ps.executeUpdate();
		} finally {
			DBUtil.close(ps);
		}
	}

	/**
	 * Inserting a new course into DB.
	 * 
	 * @param con
	 * @param course
	 * @throws SQLException
	 */
	@Override
	public void createCourse(Connection con, Course course) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(CREATE_COURSE);
			ps.setString(1, course.getTitle());
			ps.setString(2, course.getTheme());
			ps.setInt(3, course.getTeacherId());
			ps.setDate(4, new java.sql.Date(course.getStartDate().getTime()));
			ps.setDate(5, new java.sql.Date(course.getFinishDate().getTime()));
			ps.setInt(6, course.getLength());
			ps.executeUpdate();
		} finally {
			DBUtil.close(ps);
		}
	}

}
