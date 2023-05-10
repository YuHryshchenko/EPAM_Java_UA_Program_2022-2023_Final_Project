package com.hryshchenko.command;

import com.hryshchenko.constant.PagesConst;
import com.hryshchenko.dao.*;
import com.hryshchenko.dao.mysql.MySqlDAOFactory;
import com.hryshchenko.dao.mysql.MySqlUserDAO;
import com.hryshchenko.entity.Course;
import com.hryshchenko.entity.CourseStudent;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class MarkOpenCommandTest {
    private Connection con;
    private DAOFactory daoFactory;
    private PreparedStatement ps;
    private UserDAO userDAO;
    private UserManager userManager;
    private MarkOpenCommand markOpenCommand;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    private Course course;
    private List<CourseStudent> students;

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
        course = Mockito.mock(Course.class);
        userManager = Mockito.mock(UserManager.class);

        when(daoFactory.getUserDAO()).thenReturn(userDAO);
        try {
            when(daoFactory.getConnection()).thenReturn(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        markOpenCommand = new MarkOpenCommand();
    }

    @After
    public void tearDown() {
        DBUtil.close(ps);
        DBUtil.close(con);
    }


    @Test
    public void testExecute() throws DBException, AppException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("course")).thenReturn(course);
        when(UserManager.getInstance().getStudentsOfCourse(course)).thenReturn(students);

        String result = markOpenCommand.execute(request, response);

        assertEquals(PagesConst.MARK_SHEET, result);
    }

}
