package com.hryshchenko.command;

import com.hryshchenko.constant.PagesConst;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Logout Command.
 * 
 * @author Hryshchenko
 *
 */
public class LogoutCommand implements Command {
	private static final Logger log = LogManager.getLogger(LogoutCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		log.debug("Logout Command starts");
		String address = PagesConst.INDEX;
		HttpSession session = request.getSession(false);

		if (session != null) {
			session.invalidate();
			log.info("Session is invalidated. Users logs out from system.");
		}

		log.debug("Logout Command completed successfully");
		return address;
	}
}
