package com.hryshchenko;

import com.hryshchenko.command.AppException;
import com.hryshchenko.command.Command;
import com.hryshchenko.command.CommandContainer;
import com.hryshchenko.command.util.Localizator;
import com.hryshchenko.constant.PagesConst;
import com.hryshchenko.dao.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The main servlet.
 * 
 * @author Hryshchenko
 *
 */
@WebServlet("/controller")
public class Controller extends HttpServlet {
	private static final Logger log = LogManager.getLogger(Controller.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Starting get request.");
		String address = PagesConst.ERROR;
		String commandName = request.getParameter("command");
		Command command = CommandContainer.getCommand(commandName);
		log.trace("Command name is " + commandName);

		try {
			request.setAttribute("requestCleanURL", "controller");
			address = command.execute(request, response);
			log.trace("Address to forward to is " + address + ".");
		} catch (DBException | AppException e) {
			log.error("Exception caught in Controller " + e + ".");
			String errorMessage = Localizator.getLocalizedString(request, e.getMessage());
			request.setAttribute("errorMessage", errorMessage);
		} catch (Exception e) {
			log.error("Exception caught in Controller " + e + ".");
			String errorMessage = Localizator.getLocalizedString(request, "controller.exception");
			request.setAttribute("errorMessage", errorMessage);
		}

		log.debug("Completing get request");
		request.getRequestDispatcher(address).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Starting post request");
		String address = PagesConst.ERROR;
		String commandName = request.getParameter("command");
		Command command = CommandContainer.getCommand(commandName);
		log.trace("Command name is " + commandName);

		try {
			address = command.execute(request, response);
			log.trace("Address to redirect to is " + address + ".");
		} catch (DBException | AppException e) {
			log.error("Exception caught in Controller " + e + ".");
			String errorMessage = Localizator.getLocalizedString(request, e.getMessage());
			request.getSession().setAttribute("errorMessage", errorMessage);
		} catch (Exception e) {
			log.error("Exception caught in Controller " + e + ".");
			String errorMessage = Localizator.getLocalizedString(request, "controller.exception");
			request.setAttribute("errorMessage", errorMessage);
		}

		log.debug("Completing post request.");
		response.sendRedirect(address);
	}

}