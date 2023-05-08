package com.hryshchenko.command;

import com.hryshchenko.command.util.Localizator;
import com.hryshchenko.dao.CourseManager;
import com.hryshchenko.dao.DBException;
import com.hryshchenko.entity.Course;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class EditCourseSaveCommand implements Command {
	private static final Logger log = LogManager.getLogger(EditCourseSaveCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException, AppException {
		log.debug("EditCourseSave Command starts.");

		HttpSession session = request.getSession();
		Course editCourse = (Course) session.getAttribute("editCourse");
		log.debug("Course to edit is received.");

		String address = "controller?command=course_info&course-id=" + editCourse.getId();
		Course newCourse = null;
		try {
			newCourse = assembleNewCourse(request, editCourse);
		} catch (ParseException e) {
			log.error("Failure while getting date." + e.getMessage());
			throw new AppException("edit_course.date_failure", e);
		}

		CourseManager.getInstance().updateCourse(newCourse);
		String message = Localizator.getLocalizedString(request, "edit_course.info_message");
		session.setAttribute("infoMessage", message);

		log.debug("EditCourseSave Command completed successfully.");
		return address;
	}

	/**
	 * Assembling new course.
	 * 
	 * @param request
	 * @param editCourse
	 * @return An updated instance of course.
	 * @throws ParseException
	 */
	private Course assembleNewCourse(HttpServletRequest request, Course editCourse) throws ParseException {
		Course newCourse = new Course();
		String start = request.getParameter("start-date");
		String finish = request.getParameter("finish-date");

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = format.parse(start);
		Date finishDate = format.parse(finish);
		int length = countLength(start, finish);

		newCourse.setId(editCourse.getId());
		newCourse.setTitle(request.getParameter("title"));
		newCourse.setTheme(request.getParameter("theme"));
		newCourse.setTeacherId(Integer.parseInt(request.getParameter("teacher-id")));
		newCourse.setStartDate(startDate);
		newCourse.setFinishDate(finishDate);
		newCourse.setLength(length);

		log.debug("Updated instance of course is received.");
		return newCourse;
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

		log.debug("Updated length is received.");
		return (int) startDate.until(finishDate, ChronoUnit.DAYS);
	}
}
