package com.hryshchenko.command;

import com.hryshchenko.dao.*;
import com.hryshchenko.dao.mysql.MySqlCourseDAO;
import com.hryshchenko.dao.mysql.MySqlDAOFactory;
import com.hryshchenko.dao.mysql.MySqlUserDAO;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MarkSaveCommandTest {

    private static final String COURSE_ID_PARAM = "course-id";
    private static final String STUDENT_ID_PARAM_PREFIX = "student-id";
    private static final String MARK_PARAM_PREFIX = "mark";
    private static final int COURSE_ID = 1;
    private static final int STUDENT_ID_1 = 10;
    private static final int STUDENT_ID_2 = 11;
    private static final int MARK_1 = 90;
    private static final int MARK_2 = 85;
    private static final String EXPECTED_REDIRECT_ADDRESS = "controller?command=course_info&course-id=1";
    private static final String EXPECTED_INFO_MESSAGE = "Marks updated successfully.";

    private Connection con;
    private DAOFactory daoFactory;
    private UserDAO userDAO;
    private CourseDAO courseDAO;
    private PreparedStatement ps;
    private MarkSaveCommand markSaveCommand;
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
        userDAO = MySqlUserDAO.getInstance();
        courseDAO = MySqlCourseDAO.getInstance();
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
        markSaveCommand = new MarkSaveCommand();
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
        when(con.prepareStatement(MySqlUserDAO.GET_USER_BY_ID)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true);
        when(rs.getString("login")).thenReturn("newlogin");
        DBUtil.close(rs);

        when(request.getSession()).thenReturn(session);
        when(request.getSession(false)).thenReturn(session);

        // Mock the request
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(COURSE_ID_PARAM)).thenReturn(String.valueOf(COURSE_ID));
        when(request.getParameterValues(STUDENT_ID_PARAM_PREFIX)).thenReturn(new String[] { String.valueOf(STUDENT_ID_1), String.valueOf(STUDENT_ID_2) });
        when(request.getParameterValues(MARK_PARAM_PREFIX)).thenReturn(new String[] { String.valueOf(MARK_1), String.valueOf(MARK_2) });
    //    when(UserManager.getInstance()).thenReturn(userManager);

        // Mock the updateStudentsOfCourse method
        doNothing().when(userManager).updateStudentsOfCourse(COURSE_ID, STUDENT_ID_1, MARK_1);
        doNothing().when(userManager).updateStudentsOfCourse(COURSE_ID, STUDENT_ID_2, MARK_2);

        // Call the method under test
        String result = markSaveCommand.execute(request, response);

        // Verify the redirect address and info message
        assertEquals(EXPECTED_REDIRECT_ADDRESS, result);
//        verify(session).setAttribute(eq("infoMessage"), eq(EXPECTED_INFO_MESSAGE));
        verify(session).removeAttribute("course");
        verify(session).removeAttribute("studentsOfCourse");

        // Verify the UserManager method calls
//        verify(userManager).updateStudentsOfCourse(COURSE_ID, STUDENT_ID_1, MARK_1);
//        verify(userManager).updateStudentsOfCourse(COURSE_ID, STUDENT_ID_2, MARK_2);

        // Verify the Localizator method call
        //verifyStatic(Localizator.class);
        //Localizator.getLocalizedString(request, "edit_marks.info_message");
    }

}
