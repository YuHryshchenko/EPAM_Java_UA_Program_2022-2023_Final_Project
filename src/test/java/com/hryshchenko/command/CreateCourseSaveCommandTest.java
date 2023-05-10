package com.hryshchenko.command;

import com.hryshchenko.constant.PagesConst;
import com.hryshchenko.dao.*;
import com.hryshchenko.dao.mysql.MySqlCourseDAO;
import com.hryshchenko.dao.mysql.MySqlDAOFactory;
import com.hryshchenko.dao.mysql.MySqlUserDAO;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CreateCourseSaveCommandTest {

    private Connection con;
    private DAOFactory daoFactory;
    private UserDAO userDAO;
    private PreparedStatement ps;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private UserManager userManager;
    private CourseManager courseManager;
    private CreateCourseSaveCommand createCourseSaveCommand;

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
        courseManager = Mockito.mock(CourseManager.class);

        when(daoFactory.getUserDAO()).thenReturn(userDAO);
        try {
            when(daoFactory.getConnection()).thenReturn(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        createCourseSaveCommand = new CreateCourseSaveCommand();
    }

    @After
    public void tearDown() {
        DBUtil.close(ps);
        DBUtil.close(con);
    }

    @Test
    public void testExecute() throws DBException, AppException, ParseException, SQLException {

        ResultSet rs = Mockito.mock(ResultSet.class);
        when(con.prepareStatement(MySqlCourseDAO.CREATE_COURSE)).thenReturn(ps);
        when(con.prepareStatement(MySqlCourseDAO.UPDATE_COURSE)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true);
        when(rs.getString("login")).thenReturn("newlogin");
        DBUtil.close(rs);

        when(request.getSession()).thenReturn(session);
        when(request.getSession(false)).thenReturn(session);

        // Mocking request parameters
        when(request.getParameter("title")).thenReturn("Test Course");
        when(request.getParameter("theme")).thenReturn("Test Theme");
        when(request.getParameter("teacher-id")).thenReturn("1");
        when(request.getParameter("start-date")).thenReturn("2023-05-08");
        when(request.getParameter("finish-date")).thenReturn("2023-05-12");

        // Creating a mock Course object
        Course course = new Course();
        course.setTitle("Test Course");
        course.setTheme("Test Theme");
        course.setTeacherId(1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        course.setStartDate(dateFormat.parse("2023-05-08"));
        course.setFinishDate(dateFormat.parse("2023-05-12"));
        course.setLength(4);

    //    when(courseManager.createCourse(course)).thenReturn(course);

        // Creating an instance of the CreateCourseSaveCommand and calling execute()
        String result = createCourseSaveCommand.execute(request, response);

        // Verifying that the CourseManager's createCourse method was called with the mock Course object
        //verify(createCourseSaveCommand).createCourse(course);

        // Verifying that the result is the expected page address
        assertEquals(PagesConst.INDEX, result);
    }

}
