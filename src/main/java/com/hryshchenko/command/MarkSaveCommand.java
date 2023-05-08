package com.hryshchenko.command;

import com.hryshchenko.command.util.Localizator;
import com.hryshchenko.dao.DBException;
import com.hryshchenko.dao.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Command of editing marks sheet.
 * 
 * @author Hryshchenko
 *
 */
public class MarkSaveCommand implements Command {
	private static final Logger log = LogManager.getLogger(MarkSaveCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException, AppException {
		log.debug("MarkSave Command starts.");
		updateMarks(request);

		HttpSession session = request.getSession();
		session.removeAttribute("course");
		session.removeAttribute("studentsOfCourse");
		log.debug("Unused attributes are removed from session.");

		int courseId = Integer.parseInt(request.getParameter("course-id"));
		String address = "controller?command=course_info&course-id=" + courseId;

		String message = Localizator.getLocalizedString(request, "edit_marks.info_message");
		session.setAttribute("infoMessage", message);

		log.debug("MarkSave Command completed successfully.");
		return address;
	}

	/**
	 * Method of editing marks.
	 * 
	 * @param request
	 * @throws DBException
	 */
	private void updateMarks(HttpServletRequest request) throws DBException {
		log.info("Start editing marks.");

		int courseId = Integer.parseInt(request.getParameter("course-id"));
		String[] studentsId = request.getParameterValues("student-id");
		String[] marks = request.getParameterValues("mark");
		UserManager userManager = UserManager.getInstance();

		for (int i = 0; i < studentsId.length; i++) {
			int studentId = Integer.parseInt(studentsId[i]);
			int mark = Integer.parseInt(marks[i]);
			userManager.updateStudentsOfCourse(courseId, studentId, mark);
		}
		log.info("Marks were edited successfully.");
	}
}
