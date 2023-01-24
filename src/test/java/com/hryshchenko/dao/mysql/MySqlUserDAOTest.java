package com.hryshchenko.dao.mysql;

import com.hryshchenko.dao.DAOFactory;
import com.hryshchenko.dao.DBUtil;
import com.hryshchenko.dao.UserDAO;
import com.hryshchenko.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MySqlUserDAOTest {
	private Connection con;
	private DAOFactory daoFactory;
	private UserDAO userDAO;
	private PreparedStatement ps;

	@Before
	public void setUp() {
		con = Mockito.mock(Connection.class);
		daoFactory = Mockito.mock(MySqlDAOFactory.class);
		userDAO = MySqlUserDAO.getInstance();
		ps = Mockito.mock(PreparedStatement.class);

		Mockito.when(daoFactory.getUserDAO()).thenReturn(userDAO);
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
	public void testGetUserByLogin() throws SQLException {
		ResultSet rs = Mockito.mock(ResultSet.class);
		Mockito.when(con.prepareStatement(MySqlUserDAO.GET_USER_BY_LOGIN)).thenReturn(ps);
		Mockito.when(ps.executeQuery()).thenReturn(rs);

		Mockito.when(rs.next()).thenReturn(true);
		Mockito.when(rs.getString("login")).thenReturn("admin");
		DBUtil.close(rs);

		User user = userDAO.getUser(con, "admin");
		assertEquals("admin", user.getLogin());
	}

	@Test
	public void testGetUserByLoginIsNull() throws SQLException {
		ResultSet rs = Mockito.mock(ResultSet.class);
		Mockito.when(con.prepareStatement(MySqlUserDAO.GET_USER_BY_LOGIN)).thenReturn(ps);
		Mockito.when(ps.executeQuery()).thenReturn(rs);
		Mockito.when(rs.next()).thenReturn(false);
		DBUtil.close(rs);

		User user = userDAO.getUser(con, "pushkin");
		assertNull(user);
	}

	@Test
	public void testGetUserById() throws SQLException {
		ResultSet rs = Mockito.mock(ResultSet.class);
		Mockito.when(con.prepareStatement(MySqlUserDAO.GET_USER_BY_ID)).thenReturn(ps);
		Mockito.when(ps.executeQuery()).thenReturn(rs);

		Mockito.when(rs.next()).thenReturn(true);
		Mockito.when(rs.getInt("id")).thenReturn(1);
		DBUtil.close(rs);

		User user = userDAO.getUser(con, 1);
		assertEquals(1, user.getId());
	}

	@Test
	public void testGetUserByIdIsNull() throws SQLException {
		ResultSet rs = Mockito.mock(ResultSet.class);
		Mockito.when(con.prepareStatement(MySqlUserDAO.GET_USER_BY_ID)).thenReturn(ps);
		Mockito.when(ps.executeQuery()).thenReturn(rs);
		Mockito.when(rs.next()).thenReturn(false);
		DBUtil.close(rs);

		User user = userDAO.getUser(con, 2020);
		assertNull(user);
	}

	@Test
	public void testGetUsers() throws SQLException {
		ResultSet rs = Mockito.mock(ResultSet.class);
		Mockito.when(con.prepareStatement(MySqlUserDAO.GET_USERS)).thenReturn(ps);
		Mockito.when(ps.executeQuery()).thenReturn(rs);

		Mockito.when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		Mockito.when(rs.getString("login")).thenReturn("admin").thenReturn("smith").thenReturn("petrov");
		DBUtil.close(rs);

		List<User> users = userDAO.getUsers(con);
		assertEquals(3, users.size());
	}

	@Test
	public void testGetUsersSingleUser() throws SQLException {
		ResultSet rs = Mockito.mock(ResultSet.class);
		Mockito.when(con.prepareStatement(MySqlUserDAO.GET_USERS)).thenReturn(ps);
		Mockito.when(ps.executeQuery()).thenReturn(rs);

		Mockito.when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		Mockito.when(rs.getString("login")).thenReturn("admin").thenReturn("smith").thenReturn("petrov");
		DBUtil.close(rs);

		List<User> users = userDAO.getUsers(con);
		assertEquals("admin", users.get(0).getLogin());
	}

}