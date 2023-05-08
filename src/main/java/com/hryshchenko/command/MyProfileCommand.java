package com.hryshchenko.command;

import com.hryshchenko.command.util.Filtering;
import com.hryshchenko.command.util.Paginator;
import com.hryshchenko.command.util.Sorting;
import com.hryshchenko.constant.PagesConst;
import com.hryshchenko.dao.CourseManager;
import com.hryshchenko.dao.DBException;
import com.hryshchenko.entity.Course;
import com.hryshchenko.entity.CourseStudent;
import com.hryshchenko.entity.Role;
import com.hryshchenko.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Command of getting authorized user profile.
 * 
 * @author Hryshchenko
 *
 */
public class MyProfileCommand implements Command {
	private static final Logger log = LogManager.getLogger(MyProfileCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException, AppException {
		log.debug("MyProfile Command starts.");
		String address = PagesConst.PROFILE;

		log.debug("Starting getting parameters.");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("authorizedUser");
		Role role = (Role) session.getAttribute("role");
		int id = user.getId();
		log.debug("Parameters are received.");

		if (role.getName().equals("teacher")) {
			setTeacher(request, id);
		} else if (role.getName().equals("student")) {
			setStudent(request, user);
		}

		log.debug("MyProfile Command completed successfully.");
		return address;
	}

	/**
	 * Setting up teacher's profile.
	 * 
	 * @param request
	 * @param id
	 * @throws DBException
	 * @throws AppException
	 */
	private void setTeacher(HttpServletRequest request, int id) throws DBException, AppException {
		log.debug("Starting setting up teacher's profile.");
		String tab = request.getParameter("tab");
		String sort = request.getParameter("sort");
		int page = Integer.parseInt(request.getParameter("page"));

		List<Course> list = CourseManager.getInstance().getCoursesByTeacher(id);
		list = filter(tab, list);
		list = sort(sort, list);

		request = Paginator.setPagination(request, list, page);
		log.debug("Setting up teacher's profile is finished.");
	}

	/**
	 * Filtering given list of courses.
	 * 
	 * @param tab
	 * @param list
	 * @return Filtered given list of courses according to given condition.
	 * @throws AppException
	 */
	private List<Course> filter(String tab, List<Course> list) throws AppException {
		if (tab.equalsIgnoreCase("past")) {
			list = Filtering.filterFinishedCourses(list);
			log.debug("Filtered courses are past.");
		} else if (tab.equalsIgnoreCase("future")) {
			list = Filtering.filterFutureCourses(list);
			log.debug("Filtered courses are future.");
		} else {
			list = Filtering.filterCurrentCourses(list);
			log.debug("Filtered courses are current.");
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
			list = Sorting.sortCourses(Sorting.byTitleDescending, list);
			log.debug("Courses are sorted by title descending.");
		} else if (sort.equalsIgnoreCase("length")) {
			list = Sorting.sortCourses(Sorting.byLength, list);
			log.debug("Courses are sorted by length.");
		} else if (sort.equalsIgnoreCase("students")) {
			list = Sorting.sortCourses(Sorting.byStudents, list);
			log.debug("Courses are sorted by amount of students.");
		} else {
			list = Sorting.sortCourses(Sorting.byTitleAscending, list);
			log.debug("Courses are sorted by title ascending.");
		}
		return list;
	}

	/**
	 * Setting up student's profile.
	 * 
	 * @param request
	 * @param user
	 * @throws DBException
	 * @throws AppException
	 */
	private void setStudent(HttpServletRequest request, User user) throws DBException, AppException {
		log.debug("Starting setting up student's profile.");
		String tab = request.getParameter("tab");
		String sort = request.getParameter("sort");
		int page = Integer.parseInt(request.getParameter("page"));

		List<CourseStudent> list = CourseManager.getInstance().getCoursesOfStudent(user);
		request.getSession().setAttribute("fullList", list);
		list = filterCourseStudents(tab, list);
		list = sortCourseStudents(sort, list);

		request = Paginator.setPagination(request, list, page);
		log.debug("Setting up student's profile is finished.");
	}

	/**
	 * Filtering given list of courses in CourseStudent entries.
	 * 
	 * @param tab
	 * @param list
	 * @return Filtered given list of CourseStudent entities
	 *  * @throws AppException
	 */
	private List<CourseStudent> filterCourseStudents(String tab, List<CourseStudent> list) throws AppException {
		if (tab.equalsIgnoreCase("past")) {
			list = Filtering.filterFinishedCourseStudents(list);
			log.debug("Filtered courses are past.");
		} else if (tab.equalsIgnoreCase("future")) {
			list = Filtering.filterFutureCourseStudents(list);
			log.debug("Filtered courses are future.");
		} else {
			list = Filtering.filterCurrentCourseStudents(list);
			log.debug("Filtered courses are current.");
		}
		return list;
	}

	/**
	 * Sorting given list of CourseStudent entities.
	 * 
	 * @param sort
	 * @param list
	 * @return Sorted list of CourseStudent entities.
	 */
	private List<CourseStudent> sortCourseStudents(String sort, List<CourseStudent> list) {
		if (sort.equalsIgnoreCase("za")) {
			list = Sorting.sortCourseStudents(Sorting.byCourseTitleDescending, list);
			log.debug("Courses are sorted by title descending.");
		} else if (sort.equalsIgnoreCase("length")) {
			list = Sorting.sortCourseStudents(Sorting.byCourseLength, list);
			log.debug("Courses are sorted by length.");
		} else if (sort.equalsIgnoreCase("students")) {
			list = Sorting.sortCourseStudents(Sorting.byCourseStudents, list);
			log.debug("Courses are sorted by amount of students.");
		} else {
			list = Sorting.sortCourseStudents(Sorting.byCourseTitleAscending, list);
			log.debug("Courses are sorted by title ascending.");
		}
		return list;
	}
}
