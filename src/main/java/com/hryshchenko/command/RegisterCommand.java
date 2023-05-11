package com.hryshchenko.command;

import com.hryshchenko.command.util.Localizator;
import com.hryshchenko.command.util.MessageSender;
import com.hryshchenko.command.util.PasswordUtil;
import com.hryshchenko.command.util.SaltGenerator;
import com.hryshchenko.constant.PagesConst;
import com.hryshchenko.dao.DBException;
import com.hryshchenko.dao.UserManager;
import com.hryshchenko.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command of registration a new user.
 * 
 * @author Hryshchenko
 *
 */
public class RegisterCommand implements Command {
	private static final Logger log = LogManager.getLogger(RegisterCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException, AppException {
		log.debug("Register Command starts.");
		String address = PagesConst.INDEX;

		log.debug("Starting getting parameters for Register Command.");
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String firstName = request.getParameter("first-name");
		String lastName = request.getParameter("last-name");
		int roleId = -1;
		if (request.getParameter("role-id") == null) {
			roleId = 2;
		} else {
			roleId = Integer.parseInt(request.getParameter("role-id"));
		}
		log.debug("Parameters for Register command are received.");


		User user = assembleUser(login, password, email, firstName, lastName, roleId);
		UserManager.getInstance().register(user);
		sendRegistrationMessage(email, firstName, lastName);


		String message = Localizator.getLocalizedString(request, "register.info_message");
		request.getSession().setAttribute("infoMessage", message);

		log.debug("Register Command completed successfully.");
		return address;
	}

	/**
	 * Assembling an instance of User from parameters.
	 * 
	 * @param login
	 * @param password
	 * @param email
	 * @param firstName
	 * @param lastName
	 * @param roleId
	 * @return An instance of User.
	 */
	private User assembleUser(String login, String password, String email, String firstName, String lastName,
			int roleId) {
		User user = new User();
		String salt = SaltGenerator.generateSalt();
		String hashedPassword = PasswordUtil.hashSaltedPassword(password, salt);

		user.setLogin(login);
		user.setPassword(hashedPassword);
		user.setSalt(salt);
		user.setEmail(email);
		user.setStatus("active");
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setRoleId(roleId);

		log.info("Registering user " + user + ".");
		return user;
	}

	/**
	 * Sending an email about registration.
	 * 
	 * @param to
	 * @param firstName
	 * @param lastName
	 * @throws AppException
	 */
	private void sendRegistrationMessage(String to, String firstName, String lastName) throws AppException {
		String subject = "Registration on Facultative Courses website.";
		String sendMessage = "Dear " + firstName + " " + lastName
				+ ", you have successfully registered on Facultative Courses website. Now you can subscribe to available courses.";
		MessageSender.sendMessage(to, subject, sendMessage);
		log.info("Registration message has been sent to" + to);
	}
}
