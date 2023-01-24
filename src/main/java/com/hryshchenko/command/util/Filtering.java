package com.hryshchenko.command.util;

import com.hryshchenko.command.AppException;
import com.hryshchenko.entity.Course;
import com.hryshchenko.entity.CourseStudent;
import com.hryshchenko.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Util class containing tools for filtering data in containers.
 * 
 * @author Hryshchenko
 *
 */
public class Filtering {
	private static final Logger log = LogManager.getLogger(Filtering.class);

	/**
	 * Filtering courses in list by given teacher.
	 * 
	 * @param teacherId
	 * @param list
	 * @return List of courses filtered by teacher.
	 */
	public static List<Course> filterList(int teacherId, List<Course> list) {
		log.info("Filtering list of courses by teacher id" + teacherId);
		return list.stream().filter(c -> c.getTeacherId() == teacherId).collect(Collectors.toList());
	}

	/**
	 * Filtering courses in list by given theme.
	 * 
	 * @param theme
	 * @param list
	 * @return List of courses filtered by theme.
	 */
	public static List<Course> filterList(String theme, List<Course> list) {
		log.info("Filtering list of courses by theme " + theme);
		return list.stream().filter(c -> c.getTheme().equalsIgnoreCase(theme)).collect(Collectors.toList());
	}

	/**
	 * Filtering courses in list by given teacher and theme.
	 * 
	 * @param teacherId
	 * @param theme
	 * @param list
	 * @return List of courses by teacher and theme.
	 */
	public static List<Course> filterList(int teacherId, String theme, List<Course> list) {
		log.info("Filtering list of courses by teacher id " + teacherId + " and theme " + theme);
		return list.stream().filter(c -> c.getTeacherId() == teacherId)
				.filter(c -> c.getTheme().equalsIgnoreCase(theme)).collect(Collectors.toList());
	}

	/**
	 * Getting list of current courses.
	 * 
	 * @param list
	 * @return Filtered list of current courses.
	 * @throws AppException
	 */
	public static List<Course> filterCurrentCourses(List<Course> list) throws AppException {
		log.info("Filtering courses to get current ones.");
		Date currentDate = DateUtil.getCurrentDate();
		return list.stream().filter(c -> c.getStartDate().before(currentDate))
				.filter(c -> c.getFinishDate().after(currentDate)).collect(Collectors.toList());
	}

	/**
	 * Getting list of finished courses.
	 * 
	 * @param list
	 * @return Filtered list of finished courses.
	 * @throws AppException
	 */
	public static List<Course> filterFinishedCourses(List<Course> list) throws AppException {
		log.info("Filtering courses to get finished ones.");
		Date currentDate = DateUtil.getCurrentDate();
		return list.stream().filter(c -> c.getFinishDate().before(currentDate)).collect(Collectors.toList());
	}

	/**
	 * Getting list of future courses.
	 * 
	 * @param list
	 * @return Filtered list of future courses.
	 * @throws AppException
	 */
	public static List<Course> filterFutureCourses(List<Course> list) throws AppException {
		log.info("Filtering courses to get future ones.");
		Date currentDate = DateUtil.getCurrentDate();
		return list.stream().filter(c -> c.getStartDate().after(currentDate)).collect(Collectors.toList());
	}

	/**
	 * Getting list of current courses in CourseStudent entries.
	 * 
	 * @param list
	 * @return Filtered list of current courseStudent entries.
	 * @throws AppException
	 */
	public static List<CourseStudent> filterCurrentCourseStudents(List<CourseStudent> list) throws AppException {
		log.info("Filtering courses in CourseStudent entries to get current ones.");
		Date currentDate = DateUtil.getCurrentDate();
		return list.stream().filter(c -> c.getCourse().getStartDate().before(currentDate))
				.filter(c -> c.getCourse().getFinishDate().after(currentDate)).collect(Collectors.toList());
	}

	/**
	 * Getting list of finished courses in CourseStudents entries.
	 * 
	 * @param list
	 * @return Filtered list of finished CourseStudent entries.
	 * @throws AppException
	 */
	public static List<CourseStudent> filterFinishedCourseStudents(List<CourseStudent> list) throws AppException {
		log.info("Filtering courses in CourseStudent entries to get finished ones.");
		Date currentDate = DateUtil.getCurrentDate();
		return list.stream().filter(c -> c.getCourse().getFinishDate().before(currentDate))
				.collect(Collectors.toList());
	}

	/**
	 * Getting list of future courses in CourseStudent entries.
	 * 
	 * @param list
	 * @return Filtered list of future CourseStudents entries.
	 * @throws AppException
	 */
	public static List<CourseStudent> filterFutureCourseStudents(List<CourseStudent> list) throws AppException {
		log.info("Filtering courses in CourseStudent entries to get future ones.");
		Date currentDate = DateUtil.getCurrentDate();
		return list.stream().filter(c -> c.getCourse().getStartDate().after(currentDate)).collect(Collectors.toList());
	}

	/**
	 * Getting teachers from all users.
	 * 
	 * @param list
	 * @return List of teachers.
	 */
	public static List<User> filterTeachers(List<User> list) {
		log.info("Filtering teachers from list of users.");
		return list.stream().filter(u -> u.getRoleId() == 1).collect(Collectors.toList());
	}

	/**
	 * Getting students from all users.
	 * 
	 * @param list
	 * @return List of users.
	 */
	public static List<User> filterStudents(List<User> list) {
		log.info("Filtering  students from list of users.");
		return list.stream().filter(u -> u.getRoleId() == 2).collect(Collectors.toList());
	}

	/**
	 * Private constructor
	 */
	private Filtering() {

	}
}
