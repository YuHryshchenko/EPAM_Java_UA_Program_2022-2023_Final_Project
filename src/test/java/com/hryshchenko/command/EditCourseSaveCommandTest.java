package com.hryshchenko.command;

import com.hryshchenko.dao.DBException;
import com.hryshchenko.entity.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EditCourseSaveCommandTest {


    private EditCourseSaveCommand editCourseSaveCommand;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private Course editCourse;
    private Course newCourse;
    private String startDate;
    private String finishDate;
    private int length;

    @BeforeEach
    public void setUp() throws ParseException {
        editCourseSaveCommand = new EditCourseSaveCommand();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        editCourse = new Course();
        startDate = "2022-01-01";
        finishDate = "2022-01-15";
        length = 14;
        editCourse.setId(1);
        editCourse.setTitle("Title");
        editCourse.setTheme("Theme");
        editCourse.setTeacherId(1);
        editCourse.setStartDate(new Date());
        editCourse.setFinishDate(new Date());
        editCourse.setLength(14);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("editCourse")).thenReturn(editCourse);
        when(request.getParameter("title")).thenReturn("New title");
        when(request.getParameter("theme")).thenReturn("New theme");
        when(request.getParameter("teacher-id")).thenReturn("2");
        when(request.getParameter("start-date")).thenReturn(startDate);
        when(request.getParameter("finish-date")).thenReturn(finishDate);
    }

    @Test
    public void testExecute() throws DBException, AppException, ParseException {
        newCourse = editCourseSaveCommand.assembleNewCourse(request, editCourse);
        assertEquals("New title", newCourse.getTitle());
        assertEquals("New theme", newCourse.getTheme());
        assertEquals(2, newCourse.getTeacherId());
        assertEquals(length, newCourse.getLength());
    }

}
