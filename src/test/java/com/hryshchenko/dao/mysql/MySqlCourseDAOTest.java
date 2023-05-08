package com.hryshchenko.dao.mysql;

import com.hryshchenko.dao.CourseDAO;
import com.hryshchenko.dao.DAOFactory;
import com.hryshchenko.dao.DBUtil;
import com.hryshchenko.entity.Course;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class MySqlCourseDAOTest {
	private Connection con;
	private DAOFactory daoFactory;
	private CourseDAO courseDAO;
	private PreparedStatement ps;

	@Before
	public void setUp() {
		con = Mockito.mock(Connection.class);
		daoFactory = Mockito.mock(MySqlDAOFactory.class);
		courseDAO = MySqlCourseDAO.getInstance();
		ps = Mockito.mock(PreparedStatement.class);

		Mockito.when(daoFactory.getCourseDAO()).thenReturn(courseDAO);
		try {
			Mockito.when(daoFactory.getConnection()).thenReturn(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() {
		DBUtil.close(ps);
		DBUtil.close(con);
	}

	@Test
	public void testGetThemes() throws SQLException {
		ResultSet rs = Mockito.mock(ResultSet.class);
		Mockito.when(con.prepareStatement(MySqlCourseDAO.GET_THEMES)).thenReturn(ps);
		Mockito.when(ps.executeQuery()).thenReturn(rs);

		Mockito.when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		Mockito.when(rs.getString("theme")).thenReturn("English").thenReturn("German").thenReturn("French");
		DBUtil.close(rs);

		Set<String> themes = courseDAO.getThemes(con);
		assertEquals(3, themes.size());
	}

	@Test
	public void testGetThemesCheckTheme() throws SQLException {
		ResultSet rs = Mockito.mock(ResultSet.class);
		Mockito.when(con.prepareStatement(MySqlCourseDAO.GET_THEMES)).thenReturn(ps);
		Mockito.when(ps.executeQuery()).thenReturn(rs);

		Mockito.when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		Mockito.when(rs.getString("theme")).thenReturn("English").thenReturn("German").thenReturn("French");
		DBUtil.close(rs);

		Set<String> themes = courseDAO.getThemes(con);
		assertTrue(themes.contains("English"));
	}

	@Test
	public void testGetCourse() throws SQLException {
		ResultSet rs = Mockito.mock(ResultSet.class);
		Mockito.when(con.prepareStatement(MySqlCourseDAO.GET_COURSE)).thenReturn(ps);
		Mockito.when(ps.executeQuery()).thenReturn(rs);

		Mockito.when(rs.next()).thenReturn(true);
		Mockito.when(rs.getInt("id")).thenReturn(1);
		Mockito.when(rs.getString("title")).thenReturn("Elementary English");
		DBUtil.close(rs);

		Course course = courseDAO.getCourse(con, 1);
		assertEquals("Elementary English", course.getTitle());
		assertFalse(1 != course.getId());
	}

	@Test
	public void testGetCourseIsNull() throws SQLException {
		ResultSet rs = Mockito.mock(ResultSet.class);
		Mockito.when(con.prepareStatement(MySqlCourseDAO.GET_COURSE)).thenReturn(ps);
		Mockito.when(ps.executeQuery()).thenReturn(rs);

		Mockito.when(rs.next()).thenReturn(false);
		DBUtil.close(rs);

		Course course = courseDAO.getCourse(con, 58354);
		assertNull(course);
	}

	@Test
	public void testGetCourses() throws SQLException {
		ResultSet rs = Mockito.mock(ResultSet.class);
		Mockito.when(con.prepareStatement(MySqlCourseDAO.GET_COURSES)).thenReturn(ps);
		Mockito.when(ps.executeQuery()).thenReturn(rs);

		Mockito.when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		Mockito.when(rs.getString("title")).thenReturn("Elementary English").thenReturn("Elementary German")
				.thenReturn("Elementary French");
		DBUtil.close(rs);

		List<Course> list = courseDAO.getCourses(con);
		assertNotNull(list);
		assertTrue(3 == list.size());
	}

	@Test
	public void testGetCoursesCheckCourse() throws SQLException {
		ResultSet rs = Mockito.mock(ResultSet.class);
		Mockito.when(con.prepareStatement(MySqlCourseDAO.GET_COURSES)).thenReturn(ps);
		Mockito.when(ps.executeQuery()).thenReturn(rs);

		Mockito.when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		Mockito.when(rs.getString("title")).thenReturn("Elementary English").thenReturn("Elementary German")
				.thenReturn("Elementary French");
		DBUtil.close(rs);

		List<Course> list = courseDAO.getCourses(con);
		assertEquals("Elementary French", list.get(2).getTitle());
	}

	@Test
	public void testGetCoursesOfTeacher() throws SQLException {
		ResultSet rs = Mockito.mock(ResultSet.class);
		Mockito.when(con.prepareStatement(MySqlCourseDAO.GET_COURSES_OF_TEACHER)).thenReturn(ps);
		Mockito.when(ps.executeQuery()).thenReturn(rs);

		Mockito.when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		Mockito.when(rs.getString("title")).thenReturn("Elementary Spanish").thenReturn("Elementary Italian");
		DBUtil.close(rs);

		List<Course> list = courseDAO.getCoursesByTeacher(con, 6);
		assertEquals(2, list.size());
	}

	@Test
	public void testGetCoursesOfTeacherCheckCourse() throws SQLException {
		ResultSet rs = Mockito.mock(ResultSet.class);
		Mockito.when(con.prepareStatement(MySqlCourseDAO.GET_COURSES_OF_TEACHER)).thenReturn(ps);
		Mockito.when(ps.executeQuery()).thenReturn(rs);

		Mockito.when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		Mockito.when(rs.getString("title")).thenReturn("Elementary Spanish").thenReturn("Elementary Italian");
		DBUtil.close(rs);

		List<Course> list = courseDAO.getCoursesByTeacher(con, 6);
		assertEquals("Elementary Spanish", list.get(0).getTitle());
	}

}
