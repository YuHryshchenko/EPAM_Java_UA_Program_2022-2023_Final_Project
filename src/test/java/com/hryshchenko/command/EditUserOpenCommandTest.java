package com.hryshchenko.command;

import com.hryshchenko.constant.PagesConst;
import com.hryshchenko.dao.*;
import com.hryshchenko.dao.mysql.MySqlDAOFactory;
import com.hryshchenko.dao.mysql.MySqlUserDAO;
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
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EditUserOpenCommandTest {

    private Connection con;
    private DAOFactory daoFactory;
    private UserDAO userDAO;
    private PreparedStatement ps;

    private EditUserOpenCommand editUserOpenCommand;

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
        editUserOpenCommand = new EditUserOpenCommand();
    }

    @After
    public void tearDown() {
        DBUtil.close(ps);
        DBUtil.close(con);
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

        final int id = 1;
        final String expectedAddress = PagesConst.EDIT_USER;

        User editUser = new User();
        editUser.setId(id);

        when(request.getParameter("id")).thenReturn(String.valueOf(id));
        when(request.getSession()).thenReturn(session);
        //when(UserManager.getInstance()).thenReturn(userManager);
        when(userManager.getUserById(id)).thenReturn(editUser);

        String actualAddress = editUserOpenCommand.execute(request, response);
    //    DBUtil.close(rs);

        verify(request, times(1)).getParameter("id");
        verify(request, times(1)).getSession();
     //   verify(UserManager.getInstance(), times(1)).getUserById(id);
     //   verify(session, times(1)).setAttribute("editUser", editUser);

        assertEquals(expectedAddress, actualAddress);
    }
}
