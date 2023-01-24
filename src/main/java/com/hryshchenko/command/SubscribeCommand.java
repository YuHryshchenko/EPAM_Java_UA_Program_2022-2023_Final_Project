package com.hryshchenko.command;

import com.hryshchenko.command.util.Localizator;
import com.hryshchenko.dao.CourseManager;
import com.hryshchenko.dao.DBException;
import com.hryshchenko.entity.CourseStudent;
import com.hryshchenko.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Command of subscription to course.
 * 
 * @author Hryshchenko
 *
 */
public class SubscribeCommand implements Command {
	private static final Logger log = LogManager.getLogger(SubscribeCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
		log.debug("Subscribe Command starts");

		int courseId = Integer.parseInt(request.getParameter("course-id"));
		int studentId = Integer.parseInt(request.getParameter("student-id"));
		log.debug("Parameters are received.");

		String address = "controller?command=courseinfo&course-id=" + courseId;
		HttpSession session = request.getSession();
		User authorizedUser = (User) session.getAttribute("authorizedUser");

		CourseManager.getInstance().subscribeToCourse(courseId, studentId);
		List<CourseStudent> list = CourseManager.getInstance().getCoursesOfStudent(authorizedUser);
		session.setAttribute("fullList", list);
		log.debug("Substituted list of student's courses in session.");

		String message = Localizator.getLocalizedString(request, "subscribe.info_message");
		session.setAttribute("infoMessage", message);

		log.debug("Subscribe Command completed successfully");
		return address;
	}
}