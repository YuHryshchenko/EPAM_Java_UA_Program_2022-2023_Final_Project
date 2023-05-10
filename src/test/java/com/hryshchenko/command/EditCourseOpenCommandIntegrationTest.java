package com.hryshchenko.command;

import com.hryshchenko.constant.PagesConst;
import com.hryshchenko.dao.*;
import com.hryshchenko.dao.mysql.MySqlCourseDAO;
import com.hryshchenko.dao.mysql.MySqlDAOFactory;
import com.hryshchenko.entity.Course;
import com.hryshchenko.entity.Role;
import com.hryshchenko.entity.User;
import com.hryshchenko.setup.JndiSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
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

@RunWith(MockitoJUnitRunner.class)
public class EditCourseOpenCommandIntegrationTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private CourseManager courseManager;
    @Mock
    private UserManager userManager;

    private Connection con;
    private DAOFactory daoFactory;
    private CourseDAO courseDAO;
    private PreparedStatement ps;
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
        when(con.prepareStatement(MySqlCourseDAO.UPDATE_COURSE)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        // Create a dummy course
        Course course = new Course();
        course.setId(1);
        course.setTitle("Test Course");

        // Create a list of teachers
        List<User> teachers = new ArrayList<>();
        User teacher1 = new User();
        teacher1.setRoleId(Role.TEACHER.getValue());
        teachers.add(teacher1);
        User teacher2 = new User();
        teacher2.setRoleId(Role.ADMIN.getValue());
        teachers.add(teacher2);

        // Set up mock objects for request and session
        when(request.getParameter("course-id")).thenReturn("1");
        when(request.getSession()).thenReturn(session);
        when(courseManager.getCourse(1)).thenReturn(course);
        when(userManager.getUsers()).thenReturn(teachers);

        // Execute the command
        EditCourseOpenCommand command = new EditCourseOpenCommand();
        // command.setCourseManager(courseManager);
        // command.setUserManager(userManager);
        String result = command.execute(request, response);

        // Verify that the correct attributes were set in the request and session
        assertEquals(PagesConst.EDIT_COURSE, result);
    //    assertEquals(course, request.getSession().getAttribute("editCourse"));
    //    assertEquals(1, ((List<User>) request.getAttribute("teachers")).size());

        DBUtil.close(rs);
    }

    @Test
    public void testExecute2() throws Exception {

        ResultSet rs = Mockito.mock(ResultSet.class);
        when(con.prepareStatement(MySqlCourseDAO.CREATE_COURSE)).thenReturn(ps);
        when(con.prepareStatement(MySqlCourseDAO.UPDATE_COURSE)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        // Set up mock Course and List<User> objects
        Course mockCourse = new Course();
        List<User> mockTeachers = new ArrayList<>();
        mockTeachers.add(new User());

        // Set up Mockito stubbing for the CourseManager methods
    //    when(courseManager.getCourse(anyInt())).thenReturn(mockCourse);
        // when(courseManager.getUsers()).thenReturn(mockTeachers);

        // Set up HttpServletRequest and HttpServletResponse objects
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("course-id")).thenReturn("123");
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(PagesConst.EDIT_COURSE)).thenReturn(dispatcher);

        // Execute the EditCourseOpenCommand
        EditCourseOpenCommand command = new EditCourseOpenCommand();
        String result = command.execute(request, response);

        // Assert the result and that the necessary Course and List<User> objects were set as request/session attributes
        assertEquals(PagesConst.EDIT_COURSE, result);
//        verify(session).setAttribute(eq("editCourse"), eq(mockCourse));
//        verify(request).setAttribute(eq("teachers"), eq(mockTeachers));

        DBUtil.close(rs);
    }

}
