package com.hryshchenko.command;

import com.hryshchenko.constant.PagesConst;
import com.hryshchenko.dao.*;
import com.hryshchenko.dao.mysql.MySqlDAOFactory;
import com.hryshchenko.dao.mysql.MySqlUserDAO;
import com.hryshchenko.entity.Role;
import com.hryshchenko.entity.User;
import com.hryshchenko.setup.JndiSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class EditUserSaveCommandTest {
    private Connection con;
    private DAOFactory daoFactory;
    private UserDAO userDAO;
    private PreparedStatement ps;

    private EditUserSaveCommand editUserSaveCommand;

    private HttpServletRequest request;

    private HttpServletResponse response;

    private HttpSession session;

    private UserManager userManager;

    @Before
    public void setUp() throws NamingException {
        JndiSetup.setUpClass();
        con = Mockito.mock(Connection.class);
        daoFactory = Mockito.mock(MySqlDAOFactory.class);
        userDAO = MySqlUserDAO.getInstance();
        ps = Mockito.mock(PreparedStatement.class);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        userManager = Mockito.mock(UserManager.class);

        when(daoFactory.getUserDAO()).thenReturn(userDAO);
        try {
            when(daoFactory.getConnection()).thenReturn(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        editUserSaveCommand = new EditUserSaveCommand();
    }

    @After
    public void tearDown() {
        DBUtil.close(ps);
        DBUtil.close(con);
    }

    @Test
    public void testEditUserSaveCommand() throws SQLException, DBException, AppException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(con.prepareStatement(MySqlUserDAO.GET_USER_BY_LOGIN)).thenReturn(ps);
        when(con.prepareStatement(MySqlUserDAO.GET_USER_BY_ID)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true);
        when(rs.getString("login")).thenReturn("newlogin");
        DBUtil.close(rs);

        User editUser = new User();
        editUser.setId(2);
        editUser.setLogin("newlogin");
        editUser.setPassword("newpassword");
        editUser.setSalt("newsalt");
        editUser.setEmail("newemail");
        editUser.setStatus("newstatus");
        editUser.setFirstName("newfirstname");
        editUser.setLastName("newlastname");
        editUser.setRoleId(2);

        when(request.getSession()).thenReturn(session);
        when(request.getSession(false)).thenReturn(session);

        when(session.getAttribute("editUser")).thenReturn(editUser);
        when(request.getParameter("login")).thenReturn("newlogin");
        when(request.getParameter("password")).thenReturn("newpassword");
        when(request.getParameter("email")).thenReturn("newemail");
        when(request.getParameter("status")).thenReturn("newstatus");
        when(request.getParameter("first-name")).thenReturn("newfirstname");
        when(request.getParameter("last-name")).thenReturn("newlastname");
        when(session.getAttribute("role")).thenReturn(Role.STUDENT);
        when(userManager.getUserById(2)).thenReturn(editUser);

        String result = editUserSaveCommand.execute(request, response);

        assertEquals("index.jsp", result);
        assertEquals("newlogin", editUser.getLogin());
        assertEquals("newpassword", editUser.getPassword());
        assertEquals("newemail", editUser.getEmail());
        assertEquals("newstatus", editUser.getStatus());
        assertEquals("newfirstname", editUser.getFirstName());
        assertEquals("newlastname", editUser.getLastName());
        assertEquals(2, editUser.getRoleId());

        User user = userDAO.getUserByLogin(con, "newlogin");
        assertEquals("index.jsp", result);
        assertEquals("newlogin", user.getLogin());
    }

    @Test
    public void testEditUser() throws SQLException, DBException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(con.prepareStatement(MySqlUserDAO.GET_USER_BY_LOGIN)).thenReturn(ps);
        when(con.prepareStatement(MySqlUserDAO.GET_USER_BY_ID)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true);
        when(rs.getString("login")).thenReturn("newlogin");
        DBUtil.close(rs);

        userManager = Mockito.mock(UserManager.class);

        MockitoAnnotations.openMocks(this);

        final int id = 1;
        final String expectedAddress = PagesConst.EDIT_USER;

        User editUser = new User();
        editUser.setId(id);

        when(request.getParameter("id")).thenReturn(String.valueOf(id));
        when(request.getSession()).thenReturn(session);
        //when(UserManager.getInstance()).thenReturn(userManager);
        when(userManager.getUserById(id)).thenReturn(editUser);

        EditUserOpenCommand editUserOpenCommand = new EditUserOpenCommand();
        String actualAddress = editUserOpenCommand.execute(request, response);

        verify(request, times(1)).getParameter("id");
        verify(request, times(1)).getSession();
        //verify(userManager.getInstance(), times(1)).getUserById(id);
        //verify(session, times(1)).setAttribute("editUser", editUser);

        Assertions.assertEquals(expectedAddress, actualAddress);
    }

}
