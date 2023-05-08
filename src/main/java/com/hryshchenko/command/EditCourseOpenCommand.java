package com.hryshchenko.command;

import com.hryshchenko.command.util.Filtering;
import com.hryshchenko.constant.PagesConst;
import com.hryshchenko.dao.CourseManager;
import com.hryshchenko.dao.DBException;
import com.hryshchenko.dao.UserManager;
import com.hryshchenko.entity.Course;
import com.hryshchenko.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class EditCourseOpenCommand implements Command {
	private static final Logger log = LogManager.getLogger(EditCourseOpenCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
		log.debug("EditCourseOpen Command starts.");
		String address = PagesConst.EDIT_COURSE;

		int courseId = Integer.parseInt(request.getParameter("course-id"));
		Course editCourse = CourseManager.getInstance().getCourse(courseId);
		log.debug("Course for editing is received.");

		List<User> teachers = UserManager.getInstance().getUsers();
		teachers = Filtering.filterTeachers(teachers);
		log.debug("List of teachers is received.");

		request.getSession().setAttribute("editCourse", editCourse);
		request.setAttribute("teachers", teachers);

		log.debug("EditCourseOpen Command completed successfully.");
		return address;
	}

}
