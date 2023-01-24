package com.hryshchenko.command.util;

import com.hryshchenko.entity.CourseStudent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

/**
 * Custom tag displaying list of courses.
 * 
 * @author Hryshchenko
 *
 */
public class ShowCoursesOfStudent extends TagSupport {
	private static final Logger log = LogManager.getLogger(ShowCoursesOfStudent.class);

	private HttpServletRequest request;
	private List<CourseStudent> list;

	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		request = (HttpServletRequest) pageContext.getRequest();
		list = (List<CourseStudent>) request.getAttribute("list");
		log.trace("List of student's courses received.");

		JspWriter out = pageContext.getOut();
		try {
			if (list != null) {
				out.print("<ul class='list-group'>");
				for (CourseStudent courseStudent : list) {
					out.print("<li class='list-group-item'>");
					out.print("<a href='controller?command=courseinfo&course-id=" + courseStudent.getCourse().getId()
							+ "' class='regularlink' title='Look course info'>");
					out.print(courseStudent);
					if (courseStudent.getMark() > 0) {
						out.print(" " + courseStudent.getMark());
					}
					out.print("</a>");
					out.print("</li>");
				}
				out.print("</ul>");
				log.trace("List of student's courses is printed.");
			}
		} catch (IOException e) {
			log.error("Failure while printing list of student's courses. " + e);
		}
		return SKIP_BODY;
	}
}
