package com.hryshchenko.command;

import com.hryshchenko.command.util.Filtering;
import com.hryshchenko.constant.PagesConst;
import com.hryshchenko.dao.DBException;
import com.hryshchenko.dao.UserManager;
import com.hryshchenko.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CreateCourseOpenCommand implements Command {
	private static final Logger log = LogManager.getLogger(CreateCourseOpenCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
		log.debug("CreateCourseOpen Command starts.");
		String address = PagesConst.CREATE_COURSE;

		log.debug("Receiving teachers list.");
		List<User> teachers = UserManager.getInstance().getUsers();
		teachers = Filtering.filterTeachers(teachers);
		log.debug("Teachers list is received.");

		request.setAttribute("teachers", teachers);

		log.debug("CreateCourseOpen Command completed successfully.");
		return address;
	}
}
