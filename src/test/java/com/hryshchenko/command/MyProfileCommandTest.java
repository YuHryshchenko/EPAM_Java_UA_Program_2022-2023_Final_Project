package com.hryshchenko.command;

import com.hryshchenko.constant.PagesConst;
import com.hryshchenko.dao.DAOFactory;
import com.hryshchenko.dao.DBException;
import com.hryshchenko.dao.DBUtil;
import com.hryshchenko.dao.UserDAO;
import com.hryshchenko.dao.mysql.MySqlDAOFactory;
import com.hryshchenko.dao.mysql.MySqlUserDAO;
import com.hryshchenko.entity.Role;
import com.hryshchenko.entity.User;
import com.hryshchenko.setup.JndiSetup;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MyProfileCommandTest {

    private Connection con;
    private DAOFactory daoFactory;
    private UserDAO userDAO;
    private PreparedStatement ps;

    private MyProfileCommand myProfileCommand;

    private HttpServletRequest request;

    private HttpServletResponse response;

    private HttpSession session;

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

        when(daoFactory.getUserDAO()).thenReturn(userDAO);
        try {
            when(daoFactory.getConnection()).thenReturn(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        myProfileCommand = new MyProfileCommand();
    }

    @After
    public void tearDown() {
        DBUtil.close(ps);
        DBUtil.close(con);
    }

    @Test
    public void testExecute() throws DBException, AppException, SQLException {

        //ResultSet rs = Mockito.mock(ResultSet.class);
        //when(con.prepareStatement(MySqlUserDAO.GET_USER_BY_LOGIN)).thenReturn(ps);
        //when(con.prepareStatement(MySqlUserDAO.GET_USER_BY_ID)).thenReturn(ps);
        //when(ps.executeQuery()).thenReturn(rs);

        //when(rs.next()).thenReturn(true);
        //when(rs.getString("login")).thenReturn("newlogin");
        //DBUtil.close(rs);

        // set up mock objects and data
        User user = mock(User.class);
        when(user.getId()).thenReturn(1);
        when(session.getAttribute("authorizedUser")).thenReturn(user);
        Role role = mock(Role.class);
        when(role.getName()).thenReturn("teacher");
        when(session.getAttribute("role")).thenReturn(role);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("tab")).thenReturn("current");
        when(request.getParameter("sort")).thenReturn("title-asc");
        when(request.getParameter("page")).thenReturn("1");

        // call the command
        MyProfileCommand command = new MyProfileCommand();
        String result = command.execute(request, response);

        // assert that the command executed successfully and returned the correct page
        assertEquals(PagesConst.PROFILE, result);
    }

}
