package com.hryshchenko.command;

import com.hryshchenko.command.util.Filtering;
import com.hryshchenko.command.util.Paginator;
import com.hryshchenko.command.util.Sorting;
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
import java.util.NavigableSet;

/**
 * Command of getting list of all courses.
 * 
 * @author Hryshchenko
 *
 */
public class GetAllCoursesCommand implements Command {
	private static final Logger log = LogManager.getLogger(GetAllCoursesCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
		log.debug("GetAllCourses Command starts.");
		String address = PagesConst.COURSES;

		int teacher = Integer.parseInt(request.getParameter("teacher"));
		String theme = request.getParameter("theme");
		String sort = request.getParameter("sort");
		int page = Integer.parseInt(request.getParameter("page"));
		log.debug("Parameters are received.");

		List<Course> coursesList = CourseManager.getInstance().getCourses();
		log.debug("List of all courses is received.");

		coursesList = filter(teacher, theme, coursesList);
		coursesList = sort(sort, coursesList);
		request = setAttributes(request, coursesList, page);

		log.debug("GetAllCourses Command completed successfully.");
		return address;
	}

	/**
	 * Filtering given list of courses.
	 * 
	 * @param teacher
	 * @param theme
	 * @param list
	 * @return Filtered list of courses.
	 */
	private List<Course> filter(int teacher, String theme, List<Course> list) {
		if (teacher != 0 && theme.equalsIgnoreCase("all")) {
			log.debug("Filtering courses by teacher.");
			list = Filtering.filterListCoursesByTeacher(teacher, list);
		} else if (teacher == 0 && !theme.equalsIgnoreCase("all")) {
			log.debug("Filtering courses by theme.");
			list = Filtering.filterListCoursesByTheme(theme, list);
		} else if (teacher != 0 && !theme.equalsIgnoreCase("all")) {
			log.debug("Filtering courses by teacher and theme.");
			list = Filtering.filterListCourses(teacher, theme, list);
		}
		return list;
	}

	/**
	 * Sorting given list of courses
	 * 
	 * @param sort
	 * @param list
	 * @return Sorted list of courses
	 */
	private List<Course> sort(String sort, List<Course> list) {
		if (sort.equalsIgnoreCase("za")) {
			log.debug("Sorting courses by title descending.");
			list = Sorting.sortCourses(Sorting.byTitleDescending, list);
		} else if (sort.equalsIgnoreCase("length")) {
			log.debug("Sorting courses by length.");
			list = Sorting.sortCourses(Sorting.byLength, list);
		} else if (sort.equalsIgnoreCase("students")) {
			log.debug("Sorting courses by amount of students.");
			list = Sorting.sortCourses(Sorting.byStudents, list);
		} else {
			log.debug("Sorting courses by title ascending.");
			list = Sorting.sortCourses(Sorting.byTitleAscending, list);
		}
		return list;
	}

	/**
	 * Setting request attributes.
	 * 
	 * @param request
	 * @param coursesList
	 * @return Request with attributes
	 * @throws DBException
	 */
	private HttpServletRequest setAttributes(HttpServletRequest request, List<Course> coursesList, int page)
			throws DBException {
		List<User> teachersList = UserManager.getInstance().getUsers();
		teachersList = Filtering.filterTeachers(teachersList);
		NavigableSet<String> themesList = CourseManager.getInstance().getThemes();
		request = Paginator.setPagination(request, coursesList, page);
		request.setAttribute("teachers", teachersList);
		request.setAttribute("themes", themesList);

		log.debug("Attributes are set.");
		return request;
	}
}
