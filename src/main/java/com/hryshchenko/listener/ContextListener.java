package com.hryshchenko.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Context Listener where some system properties are set.
 * 
 * @author Hryshchenko
 *
 */

@WebListener
public class ContextListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Logger log = LogManager.getLogger(ContextListener.class);
		log.info("Loading application Facultative");

		ServletContext ctx = sce.getServletContext();

		log.debug("Initialization of localization parameters starts");
		String localesFileName = ctx.getInitParameter("locales");
		String localesFileRealPath = ctx.getRealPath(localesFileName);

		Properties locales = new Properties();
		try {
			locales.load(new FileInputStream(localesFileRealPath));
		} catch (IOException e) {
			log.error("Failure while getting localization parameters");
		}

		ctx.setAttribute("locales", locales);
		locales.list(System.out);
		log.debug("Localization parameters initialized");
	}
}