package com.hryshchenko.command;

import com.hryshchenko.command.util.Localizator;
import com.hryshchenko.command.util.MessageSender;
import com.hryshchenko.constant.AdminConst;
import com.hryshchenko.constant.PagesConst;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command of sending email to admin.
 * 
 * @author Hryshchenko
 *
 */
public class WriteAdminCommand implements Command {
	private static final Logger log = LogManager.getLogger(WriteAdminCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
		log.debug("Write Admin Command starts");
		String address = PagesConst.CONTACT;

		log.debug("Starting getting parameters for sending email.");
		String name = request.getParameter("name");
		String from = request.getParameter("email");
		String subject = request.getParameter("subject");
		String message = request.getParameter("message");

		String to = AdminConst.ADMIN_EMAIL;
		String sendMessage = "Name: " + name + System.lineSeparator() + "Email: " + from + System.lineSeparator()
				+ message;
		log.debug("All parameters are received.");

		MessageSender.sendMessage(to, subject, sendMessage);
		String infoMessage = Localizator.getLocalizedString(request, "write_admin.info_message");
		request.getSession().setAttribute("report", infoMessage);

		log.debug("Write Admin Command completed successfully");
		return address;
	}
}
