package com.hryshchenko.command;

import com.hryshchenko.constant.PagesConst;
import com.hryshchenko.dao.DBException;
import com.hryshchenko.dao.UserManager;
import com.hryshchenko.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUserOpenCommand implements Command {
	private static final Logger log = LogManager.getLogger(EditUserOpenCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
		log.debug("EditUserOpenCommand Command starts.");
		String address = PagesConst.EDIT_USER;

		int id = Integer.parseInt(request.getParameter("id"));
		User editUser = UserManager.getInstance().getUserById(id);
		log.debug("Instance of user for edit is received.");

		request.getSession().setAttribute("editUser", editUser);
		log.debug("EditUserOpen Command completed successfully.");
		return address;
	}
}
