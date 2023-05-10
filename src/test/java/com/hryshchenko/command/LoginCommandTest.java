package com.hryshchenko.command;

import com.hryshchenko.command.util.PasswordUtil;
import com.hryshchenko.dao.*;
import com.hryshchenko.dao.mysql.MySqlDAOFactory;
import com.hryshchenko.dao.mysql.MySqlUserDAO;
import com.hryshchenko.entity.User;
import com.hryshchenko.setup.JndiSetup;
import org.h2.engine.Role;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class LoginCommandTest {

    private Connection con;
    private DAOFactory daoFactory;
    private UserDAO userDAO;
    private PreparedStatement ps;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private LoginCommand loginCommand;
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
        loginCommand = new LoginCommand();
    }

    @After
    public void tearDown() {
        DBUtil.close(ps);
        DBUtil.close(con);
    }

    @Test
    public void testAuthorizeWithValidCredentials() throws DBException {
        User user = new User();
        user.setLogin("testuser");
        user.setPassword(PasswordUtil.hashSaltedPassword("testpassword", "testsalt"));
        user.setStatus("active");
//        when(UserManager.getInstance().getUserByLogin("testuser")).thenReturn(user);
        when(request.getSession()).thenReturn(session);

        boolean result = loginCommand.authorize(request, user, "testpassword");

        assertTrue(result);
    }

    @Test
    public void testAuthorizeWithInvalidCredentials() throws DBException {
        User user = new User();
        user.setLogin("testuser");
        user.setPassword(PasswordUtil.hashSaltedPassword("testpassword", "testsalt"));
        user.setStatus("active");
        when(UserManager.getInstance().getUserByLogin("testuser")).thenReturn(user);
        when(request.getSession()).thenReturn(session);

        boolean result = loginCommand.authorize(request, user, "wrongpassword");

        assertFalse(result);
    }

    @Test
    public void testAuthorizeWithBannedUser() throws DBException {
        User user = new User();
        user.setLogin("testuser");
        user.setPassword(PasswordUtil.hashSaltedPassword("testpassword", "testsalt"));
        user.setStatus("banned");
    //    when(UserManager.getInstance().getUserByLogin("testuser")).thenReturn(user);
        when(request.getSession()).thenReturn(session);

        boolean result = loginCommand.authorize(request, user, "testpassword");

        assertFalse(result);
    }

    @Test
    public void testAuthorizeWithNonexistentUser() throws DBException {
    //    when(UserManager.getInstance().getUserByLogin("testuser")).thenReturn(null);
        when(request.getSession()).thenReturn(session);

        boolean result = loginCommand.authorize(request, null, "testpassword");

        assertFalse(result);
    }


    @Test
    public void testSetAttributes() throws DBException {
        User user = new User();
        user.setId(Math.toIntExact(1L));
        user.setLogin("testuser");
        user.setPassword(PasswordUtil.hashSaltedPassword("testpassword", "testsalt"));
        user.setEmail("testuser@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setRoleId(Role.USER);
        user.setStatus("active");
    //    when(UserManager.getInstance().getUserById(Math.toIntExact(1L))).thenReturn(user);
        when(request.getSession()).thenReturn(session);

        HttpSession result = loginCommand.setAttributes(user, session);

        assertEquals(user, result.getAttribute("authorizedUser"));
        assertEquals(Role.USER, result.getAttribute("role"));
    }



}
