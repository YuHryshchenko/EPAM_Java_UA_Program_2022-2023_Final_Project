package com.hryshchenko.command;

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
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class GetAllUsersCommandTest {

    private Connection con;
    private DAOFactory daoFactory;
    private UserDAO userDAO;
    private UserManager userManager;
    private PreparedStatement ps;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private GetAllUsersCommand getAllUsersCommand;

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
        getAllUsersCommand = new GetAllUsersCommand();
    }

    @After
    public void tearDown() {
        DBUtil.close(ps);
        DBUtil.close(con);
    }

    @Test
    public void testExecute() throws DBException, SQLException {

        when(daoFactory.getUserDAO()).thenReturn(userDAO);
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(con.prepareStatement(MySqlUserDAO.GET_USER_BY_LOGIN)).thenReturn(ps);
        when(con.prepareStatement(MySqlUserDAO.GET_USER_BY_ID)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true);
        when(rs.getString("login")).thenReturn("newlogin");
        DBUtil.close(rs);

        // Mock the request parameters
        when(request.getParameter("tab")).thenReturn("teachers");
        when(request.getParameter("sort")).thenReturn("za");
        when(request.getParameter("page")).thenReturn("1");

        // Mock the UserManager and list of users
        User John = new User();
        John.setId(30);
        John.setLogin("newLoginJohn");
        John.setPassword("newPasswordJohn");
        John.setSalt("newSaltJohn");
        John.setEmail("newEmailJohn");
        John.setStatus("newStatusJohn");
        John.setFirstName("newFirstNameJohn");
        John.setLastName("newLastNameJohn");
        John.setRoleId(2);

        User Jane = new User();
        Jane.setId(31);
        Jane.setLogin("newLoginJane");
        Jane.setPassword("newPasswordJane");
        Jane.setSalt("newSaltJane");
        Jane.setEmail("newEmailJane");
        Jane.setStatus("newStatusJane");
        Jane.setFirstName("newFirstNameJane");
        Jane.setLastName("newLastNameJane");
        Jane.setRoleId(1);

        // Mock the UserManager and list of users
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(John);
        mockUsers.add(Jane);
        //   when(UserManager.getInstance().getUsers()).thenReturn(mockUsers);

        // Call the execute method and verify the results
        String result = getAllUsersCommand.execute(request, response);
        assertEquals("users.jsp", result);

//        verify(request).setAttribute("users", mockUsers);
//        verify(request).setAttribute("currentPage", 1);
//        verify(request).setAttribute("totalPages", 1);
//        verify(request).setAttribute("tab", "teachers");
//        verify(request).setAttribute("sort", "za");
    }

}
