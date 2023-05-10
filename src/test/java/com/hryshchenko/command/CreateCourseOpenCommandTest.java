package com.hryshchenko.command;

import com.hryshchenko.constant.PagesConst;
import com.hryshchenko.dao.*;
import com.hryshchenko.dao.mysql.MySqlCourseDAO;
import com.hryshchenko.dao.mysql.MySqlDAOFactory;
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

public class CreateCourseOpenCommandTest {

    private Connection con;
    private DAOFactory daoFactory;
    private CourseDAO courseDAO;
    private PreparedStatement ps;

    private CreateCourseOpenCommand createCourseOpenCommand;

    private HttpServletRequest request;

    private HttpServletResponse response;

    private HttpSession session;

    private UserManager userManager;

    private CourseManager courseManager;

    @Before
    public void setUp() throws NamingException {
        JndiSetup.setUpClass();
        con = Mockito.mock(Connection.class);
        daoFactory = Mockito.mock(MySqlDAOFactory.class);
        courseDAO = MySqlCourseDAO.getInstance();
        ps = Mockito.mock(PreparedStatement.class);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        userManager = Mockito.mock(UserManager.class);
        courseManager = Mockito.mock(CourseManager.class);

        when(daoFactory.getCourseDAO()).thenReturn(courseDAO);
        try {
            when(daoFactory.getConnection()).thenReturn(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        createCourseOpenCommand = new CreateCourseOpenCommand();
    }

    @After
    public void tearDown() {
        DBUtil.close(ps);
        DBUtil.close(con);
    }


    @Test
    public void testExecute() throws DBException, SQLException {

        ResultSet rs = Mockito.mock(ResultSet.class);
        when(con.prepareStatement(MySqlCourseDAO.UPDATE_COURSE)).thenReturn(ps);
        when(con.prepareStatement(MySqlCourseDAO.SUBSCRIBE_UPDATE_COURSE)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true);
        when(rs.getString("login")).thenReturn("newlogin");
        DBUtil.close(rs);

        // Create a mock HttpServletRequest and HttpServletResponse
        //HttpServletRequest request = mock(HttpServletRequest.class);
        //HttpServletResponse response = mock(HttpServletResponse.class);

        // Create a mock list of teachers
        List<User> teachers = new ArrayList<>();
        User teacher1 = new User();
        //teacher1.setRole(User.Role);
        User teacher2 = new User();
        //teacher2.setRole(User.Role.);
        teachers.add(teacher1);
        teachers.add(teacher2);

        // Mock the UserManager to return the mock list of teachers
        when(userManager.getUsers()).thenReturn(teachers);

        // Set the mock UserManager instance in the CommandContainer
        //CommandContainer.setDependency(UserManager.class.getName(), userManager);

        // Execute the command
        String address = createCourseOpenCommand.execute(request, response);

        // Verify that the teachers list is added to the request attribute
    //    assertEquals(teachers, request.getAttribute("teachers"));

        // Verify that the address is the expected one
        assertEquals(PagesConst.CREATE_COURSE, address);
    }

}
