package com.hryshchenko.command;

import com.hryshchenko.constant.PagesConst;
import com.hryshchenko.dao.DBException;
import com.hryshchenko.dao.UserManager;
import com.hryshchenko.entity.Course;
import com.hryshchenko.entity.CourseStudent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Command of opening marks sheet.
 * 
 * @author Hryshchenko
 *
 */
public class MarkOpenCommand implements Command {
	private static final Logger log = LogManager.getLogger(MarkOpenCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException, AppException {
		log.debug("MarkOpen Command starts.");

		String address = PagesConst.MARK_SHEET;
		HttpSession session = request.getSession();
		Course course = (Course) session.getAttribute("course");
		List<CourseStudent> studentsOfCourse = UserManager.getInstance().getStudentsOfCourse(course);
		log.debug("List of students of course is received.");

		session.setAttribute("studentsOfCourse", studentsOfCourse);
		log.debug("MarkOpen Command completed successfully.");

		return address;
	}
}
