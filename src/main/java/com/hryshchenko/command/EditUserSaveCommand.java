package com.hryshchenko.command;

import com.hryshchenko.command.util.Localizator;
import com.hryshchenko.command.util.PasswordUtil;
import com.hryshchenko.constant.PagesConst;
import com.hryshchenko.dao.DBException;
import com.hryshchenko.dao.UserManager;
import com.hryshchenko.entity.Role;
import com.hryshchenko.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditUserSaveCommand implements Command {
	private static final Logger log = LogManager.getLogger(EditUserSaveCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
		log.debug("EditUserSave Command starts");

		String address = PagesConst.INDEX;
		HttpSession session = request.getSession();
		User editUser = (User) session.getAttribute("editUser");
		log.debug("User for edit is received.");

		User newUser = assembleNewUser(request, editUser);
		UserManager.getInstance().updateUser(newUser);

		if (!isAdmin(session)) {
			session.setAttribute("authorizedUser", newUser);
			log.debug("Instance of authorizedUser is substituted.");
		}

		String message = Localizator.getLocalizedString(request, "edituser.info_message");
		session.setAttribute("infoMessage", message);

		log.debug("EditUserSave Command completed successfully");
		return address;
	}

	/**
	 * Assembling new user instance.
	 * 
	 * @param request
	 * @param editUser
	 * @return Updated user instance.
	 */
	private User assembleNewUser(HttpServletRequest request, User editUser) {
		User newUser = new User();
		String password = request.getParameter("password");
		String status = request.getParameter("status");
		if (password.isEmpty()) {
			password = editUser.getPassword();
		} else {
			String salt = editUser.getSalt();
			password = PasswordUtil.hashSaltedPassword(password, salt);
		}
		if (status == null) {
			status = editUser.getStatus();
		}

		newUser.setId(editUser.getId());
		newUser.setLogin(request.getParameter("login"));
		newUser.setPassword(password);
		newUser.setSalt(editUser.getSalt());
		newUser.setEmail(request.getParameter("email"));
		newUser.setStatus(status);
		newUser.setFirstName(request.getParameter("first-name"));
		newUser.setLastName(request.getParameter("last-name"));
		newUser.setRoleId(editUser.getRoleId());

		log.debug("Updated instance of user is received.");
		return newUser;
	}

	/**
	 * Checking if current role is admin.
	 * 
	 * @param session
	 * @return True if current role is admin, false otherwise.
	 */
	private boolean isAdmin(HttpSession session) {
		Role role = (Role) session.getAttribute("role");
		if (role.getName().equalsIgnoreCase("admin")) {
			log.debug("Current user role is admin");
			return true;
		}
		log.debug("Current user role is not admin");
		return false;
	}
}
