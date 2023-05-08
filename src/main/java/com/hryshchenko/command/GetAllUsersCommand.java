package com.hryshchenko.command;

import com.hryshchenko.command.util.Filtering;
import com.hryshchenko.command.util.Paginator;
import com.hryshchenko.command.util.Sorting;
import com.hryshchenko.constant.PagesConst;
import com.hryshchenko.dao.DBException;
import com.hryshchenko.dao.UserManager;
import com.hryshchenko.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Command of getting list of all users.
 * 
 * @author Hryshchenko
 *
 */
public class GetAllUsersCommand implements Command {
	private static final Logger log = LogManager.getLogger(GetAllUsersCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
		log.debug("GetAllUsers Command starts.");
		String address = PagesConst.USERS;

		String tab = request.getParameter("tab");
		String sort = request.getParameter("sort");
		int page = Integer.parseInt(request.getParameter("page"));
		log.debug("Parameters are received.");

		List<User> users = getFilteredList(tab);
		users = sortList(sort, users);
		request = Paginator.setPagination(request, users, page);

		log.debug("GetAllUsers Command completed successfully.");
		return address;
	}

	/**
	 * Getting filtered list of users according to given parameter.
	 * 
	 * @param tab
	 * @return Filtered list.
	 * @throws DBException
	 */
	private List<User> getFilteredList(String tab) throws DBException {
		List<User> list = UserManager.getInstance().getUsers();
		if (tab.equalsIgnoreCase("teachers")) {
			list = Filtering.filterTeachers(list);
			log.info("Received list of teachers.");
		} else if (tab.equalsIgnoreCase("students")) {
			list = Filtering.filterStudents(list);
			log.info("Received list of students.");
		}
		return list;
	}

	/**
	 * Sorting given list of users.
	 * 
	 * @param sort
	 * @param list
	 * @return Sorted list of users.
	 */
	private List<User> sortList(String sort, List<User> list) {
		if (sort.equalsIgnoreCase("za")) {
			log.debug("Sorting users by last name descending.");
			list = Sorting.sortUsers(Sorting.byNameDescending, list);
		} else {
			log.debug("Sorting users by last name ascending.");
			list = Sorting.sortUsers(Sorting.byNameAscending, list);
		}
		return list;
	}
}
