package com.hryshchenko.command;

import com.hryshchenko.command.util.Localizator;
import com.hryshchenko.constant.PagesConst;
import com.hryshchenko.dao.CourseManager;
import com.hryshchenko.dao.DBException;
import com.hryshchenko.entity.Course;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class CreateCourseSaveCommand implements Command {
	private static final Logger log = LogManager.getLogger(CreateCourseSaveCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException, AppException {
		log.debug("CreateCourseSave Command starts");
		String address = PagesConst.INDEX;

		log.debug("Starting getting parameters.");
		String title = request.getParameter("title");
		String theme = request.getParameter("theme");
		int teacherId = Integer.parseInt(request.getParameter("teacher-id"));
		String start = request.getParameter("start-date");
		String finish = request.getParameter("finish-date");
		log.debug("Parameters are received.");

		Course course = null;
		try {
			course = assembleCourse(title, theme, teacherId, start, finish);
		} catch (ParseException e) {
			log.error("Failure while getting date." + e);
			throw new AppException("createcourse.date_failure", e);
		}

		CourseManager.getInstance().createCourse(course);
		String message = Localizator.getLocalizedString(request, "createcourse.info_message");
		request.getSession().setAttribute("infoMessage", message);

		log.debug("CreateCourseSave Command completed successfully");
		return address;
	}

	/**
	 * Assembling an instance of course.
	 * 
	 * @param title
	 * @param theme
	 * @param teacherId
	 * @param start
	 * @param finish
	 * @return An instance of course.
	 * @throws ParseException
	 */
	private Course assembleCourse(String title, String theme, int teacherId, String start, String finish)
			throws ParseException {
		log.debug("Starting assembling an instance of new course.");
		Course course = new Course();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = format.parse(start);
		Date finishDate = format.parse(finish);
		int length = countLength(start, finish);

		course.setTitle(title);
		course.setTheme(theme);
		course.setTeacherId(teacherId);
		course.setStartDate(startDate);
		course.setFinishDate(finishDate);
		course.setLength(length);

		log.debug("Instance of a new course is assembled.");
		return course;
	}

	/**
	 * Counting length of course.
	 * 
	 * @param start
	 * @param finish
	 * @return Length of course.
	 */
	private int countLength(String start, String finish) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDate = LocalDate.parse(start, formatter);
		LocalDate finishDate = LocalDate.parse(finish, formatter);

		log.debug("Length of course is calculated.");
		return (int) startDate.until(finishDate, ChronoUnit.DAYS);
	}
}
