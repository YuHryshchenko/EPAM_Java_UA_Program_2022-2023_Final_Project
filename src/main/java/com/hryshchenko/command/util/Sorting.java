package com.hryshchenko.command.util;

import com.hryshchenko.entity.Course;
import com.hryshchenko.entity.CourseStudent;
import com.hryshchenko.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Util class containing tools for sorting containers.
 * 
 * @author Hryshchenko
 *
 */
public class Sorting {
	private static final Logger log = LogManager.getLogger(Sorting.class);

	/**
	 * Comparator, which sorts courses by title in ascending order.
	 */
	public static final Comparator<Course> byTitleAscending = (o1, o2) -> o1.getTitle().compareTo(o2.getTitle());

	/**
	 * Comparator, which sorts courses by title in descending order.
	 */
	public static final Comparator<Course> byTitleDescending = (o1, o2) -> o2.getTitle().compareTo(o1.getTitle());

	/**
	 * Comparator, which sorts courses by amount of students.
	 */
	public static final Comparator<Course> byStudents = (o1, o2) -> {
		if (o1.getStudents() > o2.getStudents()) {
			return -1;
		} else if (o1.getStudents() < o2.getStudents()) {
			return 1;
		}
		return 0;
	};

	/**
	 * Comparator, which sorts courses by length.
	 */
	public static final Comparator<Course> byLength = (o1, o2) -> {
		if (o1.getLength() > o2.getLength()) {
			return -1;
		} else if (o1.getLength() < o2.getLength()) {
			return 1;
		}
		return 0;
	};

	/**
	 * Comparator, which sorts courseStudent entities by title in ascending order.
	 */
	public static final Comparator<CourseStudent> byCourseTitleAscending = (o1, o2) -> o1.getCourse().getTitle()
			.compareTo(o2.getCourse().getTitle());

	/**
	 * Comparator, which sorts courseStudent entities by title in descending order.
	 */
	public static final Comparator<CourseStudent> byCourseTitleDescending = (o1, o2) -> o2.getCourse().getTitle()
			.compareTo(o1.getCourse().getTitle());

	/**
	 * Comparator, which sorts courseStudent entities by amount of students.
	 */
	public static final Comparator<CourseStudent> byCourseStudents = (o1, o2) -> {
		if (o1.getCourse().getStudents() > o2.getCourse().getStudents()) {
			return -1;
		} else if (o1.getCourse().getStudents() < o2.getCourse().getStudents()) {
			return 1;
		}
		return 0;
	};

	/**
	 * Comparator, which sorts courseStudent entities by course length.
	 */
	public static final Comparator<CourseStudent> byCourseLength = (o1, o2) -> {
		if (o1.getCourse().getLength() > o2.getCourse().getLength()) {
			return -1;
		} else if (o1.getCourse().getLength() < o2.getCourse().getLength()) {
			return 1;
		}
		return 0;
	};

	/**
	 * Comparator, which sorts users by last name in ascending order.
	 */
	public static final Comparator<User> byNameAscending = (o1, o2) -> o1.getLastName().compareTo(o2.getLastName());

	/**
	 * Comparator, which sorts users by last name in descending order.
	 */
	public static final Comparator<User> byNameDescending = (o1, o2) -> o2.getLastName().compareTo(o1.getLastName());

	/**
	 * Sorting given list of courses according to given comparator.
	 * 
	 * @param comparator
	 * @param list
	 * @return Sorted list of courses.
	 */
	public static List<Course> sortCourses(Comparator<Course> comparator, List<Course> list) {
		log.info("Courses are sorted according to given comparator.");
		return list.stream().sorted(comparator).collect(Collectors.toList());
	}

	/**
	 * Sorting CourseStudent entities according to given comparator.
	 * 
	 * @param comparator
	 * @param list
	 * @return Sorted list of CourseStudent entities.
	 */
	public static List<CourseStudent> sortCourseStudents(Comparator<CourseStudent> comparator,
			List<CourseStudent> list) {
		log.info("CourseStudent entities  are sorted according to given comparator.");
		return list.stream().sorted(comparator).collect(Collectors.toList());
	}

	/**
	 * Sorting given list of users according to given comparator.
	 * 
	 * @param comparator
	 * @param list
	 * @return Sorted list of users.
	 */
	public static List<User> sortUsers(Comparator<User> comparator, List<User> list) {
		log.info("Students are sorted according to given comparator.");
		return list.stream().sorted(comparator).collect(Collectors.toList());
	}

	/**
	 * Private constructor.
	 */
	private Sorting() {

	}
}
