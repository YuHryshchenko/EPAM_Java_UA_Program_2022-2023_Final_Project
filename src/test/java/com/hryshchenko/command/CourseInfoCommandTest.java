package com.hryshchenko.command;

import com.hryshchenko.constant.PagesConst;
import com.hryshchenko.dao.*;
import com.hryshchenko.dao.mysql.MySqlCourseDAO;
import com.hryshchenko.dao.mysql.MySqlDAOFactory;
import com.hryshchenko.entity.Course;
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
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CourseInfoCommandTest {
    private Connection con;
    private DAOFactory daoFactory;
    private PreparedStatement ps;
    private CourseDAO courseDAO;
    private CourseInfoCommand courseInfoCommand;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private CourseManager courseManager;

    @Before
    public void setUp() throws NamingException {
        con = Mockito.mock(Connection.class);
        daoFactory = Mockito.mock(MySqlDAOFactory.class);
        courseDAO = MySqlCourseDAO.getInstance();
        ps = Mockito.mock(PreparedStatement.class);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        courseManager = Mockito.mock(CourseManager.class);

        when(daoFactory.getCourseDAO()).thenReturn(courseDAO);
        try {
            when(daoFactory.getConnection()).thenReturn(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        courseInfoCommand = new CourseInfoCommand();
        JndiSetup.setUpClass();
    }

    @After
    public void tearDown() {
        DBUtil.close(ps);
        DBUtil.close(con);
    }

    @Test
    public void testExecute() throws DBException, AppException, SQLException {

        ResultSet rs = Mockito.mock(ResultSet.class);
        when(con.prepareStatement(MySqlCourseDAO.GET_COURSE)).thenReturn(ps);
        when(con.prepareStatement(MySqlCourseDAO.GET_COURSES)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true);
        when(rs.getString("login")).thenReturn("newlogin");
        DBUtil.close(rs);

        int courseId = 1;
        Course course = new Course();
        course.setId(courseId);

        Date currentDate = new Date();

        when(request.getSession()).thenReturn(session);
        when(request.getSession(false)).thenReturn(session);


        when(request.getParameter("course-id")).thenReturn(String.valueOf(courseId));
    //    when(CourseManager.getInstance()).thenReturn(courseManager);
        when(courseManager.getCourse(courseId)).thenReturn(course);
        when(request.getAttribute("currentDate")).thenReturn(currentDate);

        String result = courseInfoCommand.execute(request, response);
        assertEquals(PagesConst.COURSE_INFO, result);
    //    verify(request).setAttribute("currentDate", currentDate);
    //    verify(request).setAttribute("course", course);
    }

}
