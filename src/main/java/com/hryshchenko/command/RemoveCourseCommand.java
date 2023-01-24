package com.hryshchenko.command;

import com.hryshchenko.command.util.Localizator;
import com.hryshchenko.constant.PagesConst;
import com.hryshchenko.dao.CourseManager;
import com.hryshchenko.dao.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command removing course.
 * 
 * @author Hryshchenko
 *
 */
public class RemoveCourseCommand implements Command {
	private static final Logger log = LogManager.getLogger(RemoveCourseCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
		log.debug("RemoveCourse Command starts");
		String address = PagesConst.INDEX;

		int courseId = Integer.parseInt(request.getParameter("course-id"));
		log.debug("Parameters are received.");
		CourseManager.getInstance().removeCourse(courseId);

		String message = Localizator.getLocalizedString(request, "removecourse.info_message");
		request.getSession().setAttribute("infoMessage", message);

		log.debug("RemoveCourse Command completed successfully");
		return address;
	}
}
