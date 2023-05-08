package com.hryshchenko.command;

import com.hryshchenko.command.util.DateUtil;
import com.hryshchenko.constant.PagesConst;
import com.hryshchenko.dao.CourseManager;
import com.hryshchenko.dao.DBException;
import com.hryshchenko.entity.Course;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class CourseInfoCommand implements Command {
	private static final Logger log = LogManager.getLogger(CourseInfoCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException, AppException {
		log.debug("CourseInfo Command starts.");
		String address = PagesConst.COURSE_INFO;

		log.debug("Starting receiving parameters.");
		int courseId = Integer.parseInt(request.getParameter("course-id"));
		Course course = CourseManager.getInstance().getCourse(courseId);
		Date currentDate = DateUtil.getCurrentDate();
		log.debug("Parameters are received.");

		request.setAttribute("currentDate", currentDate);
		request.setAttribute("course", course);

		log.debug("CourseInfo Command completed successfully.");
		return address;
	}
}
