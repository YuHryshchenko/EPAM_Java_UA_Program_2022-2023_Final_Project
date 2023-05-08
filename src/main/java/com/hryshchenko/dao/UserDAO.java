package com.hryshchenko.dao;

import com.hryshchenko.entity.Course;
import com.hryshchenko.entity.CourseStudent;
import com.hryshchenko.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface which defines methods for UserDAO implementations.
 * 
 * @author Hryshchenko
 *
 */
public interface UserDAO {
	User getUserByLogin(Connection con, String login) throws SQLException;

	User getUserById(Connection con, int id) throws SQLException;

	List<User> getUsers(Connection con) throws SQLException;

	List<CourseStudent> getStudentsOfCourse(Connection con, Course course) throws SQLException;

	void updateStudentsOfCourse(Connection con, int courseId, int studentId, int mark) throws SQLException;

	void updateUser(Connection con, User user) throws SQLException;

	void register(Connection con, User user) throws SQLException;
}
