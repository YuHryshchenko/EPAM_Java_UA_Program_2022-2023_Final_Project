package com.hryshchenko.command;

import com.hryshchenko.constant.PagesConst;
import com.hryshchenko.dao.*;
import com.hryshchenko.dao.mysql.MySqlCourseDAO;
import com.hryshchenko.dao.mysql.MySqlDAOFactory;
import com.hryshchenko.entity.Course;
import com.hryshchenko.entity.User;
import com.hryshchenko.setup.JndiSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EditCourseOpenCommandTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private Connection con;
    private PreparedStatement ps;
    private DAOFactory daoFactory;
    private CourseDAO courseDAO;
    private CourseManager courseManager;
    private UserManager userManager;
    private EditCourseOpenCommand editCourseOpenCommand;

    @Before
    public void setup() {
        JndiSetup.setUpClass();
        con = Mockito.mock(Connection.class);
        daoFactory = Mockito.mock(MySqlDAOFactory.class);
        ps = Mockito.mock(PreparedStatement.class);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        courseDAO = Mockito.mock(CourseDAO.class);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        courseManager = Mockito.mock(CourseManager.class);

        Mockito.when(daoFactory.getCourseDAO()).thenReturn(courseDAO);
        try {
            Mockito.when(daoFactory.getConnection()).thenReturn(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        editCourseOpenCommand = new EditCourseOpenCommand();
    }

    @After
    public void tearDown() {
        DBUtil.close(ps);
        DBUtil.close(con);
    }

    @Test
    public void testExecute() throws DBException, SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(con.prepareStatement(MySqlCourseDAO.CREATE_COURSE)).thenReturn(ps);
        Mockito.when(con.prepareStatement(MySqlCourseDAO.UPDATE_COURSE)).thenReturn(ps);
        Mockito.when(ps.executeQuery()).thenReturn(rs);

        final int courseId = 1;
        final Course expectedEditCourse = new Course();
        expectedEditCourse.setId(courseId);

        final List<User> expectedTeachers = new ArrayList<>();
        expectedTeachers.add(new User());

        courseManager = mock(CourseManager.class);
        userManager = mock(UserManager.class);
        request = mock(HttpServletRequest.class);
        request.setAttribute("course-id", courseId);
        editCourseOpenCommand = new EditCourseOpenCommand();

        when(request.getParameter("course-id")).thenReturn(String.valueOf(courseId));
        when(request.getSession()).thenReturn(session);
        when(courseManager.getCourse(courseId)).thenReturn(expectedEditCourse);
        when(userManager.getUsers()).thenReturn(expectedTeachers);

        final String result = editCourseOpenCommand.execute(request, response);

        assertEquals(PagesConst.EDIT_COURSE, result);
    //    assertEquals(expectedEditCourse, session.getAttribute("editCourse"));
    //    assertEquals(expectedTeachers, request.getAttribute("teachers"));
    //    verify(courseManager, times(1)).getCourse(courseId);
    //    verify(userManager, times(1)).getUsers();

        DBUtil.close(rs);
    }

}
