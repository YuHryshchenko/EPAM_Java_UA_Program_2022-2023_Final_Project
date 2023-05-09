package com.hryshchenko.tag;

import com.hryshchenko.command.util.ShowCoursesOfStudent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.io.IOException;

import static javax.servlet.jsp.tagext.Tag.SKIP_BODY;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

public class TagTest {
    private static final Logger log = LogManager.getLogger(TagTest.class);
    @Test
    void testDoTag() throws IOException {
        try (JspWriter jspWriter = mock(JspWriter.class)) {
            PageContext pageContext = mock(PageContext.class);
            HttpServletRequest request = mock(HttpServletRequest.class);
            request.setAttribute("list", null);
            when(pageContext.getOut()).thenReturn(jspWriter);
            when(pageContext.getRequest()).thenReturn(request);
            ShowCoursesOfStudent showCoursesOfStudentTag = new ShowCoursesOfStudent();
            showCoursesOfStudentTag.setPageContext(pageContext);
            assertDoesNotThrow(showCoursesOfStudentTag::doStartTag);
            int tag = showCoursesOfStudentTag.doStartTag();
            Assertions.assertEquals(tag, SKIP_BODY);
        } catch (JspException e) {
            log.error("Failure while printing list of student's courses. " + e.getMessage());
        }
    }

}
