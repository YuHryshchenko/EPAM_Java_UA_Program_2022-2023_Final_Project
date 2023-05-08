package com.hryshchenko.command.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Util class serving localization.
 * 
 * @author Hryshchenko
 *
 */
public class Localizator {
	private static final Logger log = LogManager.getLogger(Localizator.class);

	private static Locale en = new Locale("en");
	private static Locale uk = new Locale("uk");
	private static ResourceBundle enRB = ResourceBundle.getBundle("resources", en);
	private static ResourceBundle ukRB = ResourceBundle.getBundle("resources", uk);

	/**
	 * Getting string value by key.
	 * 
	 * @param request
	 * @param key
	 * @return String value.
	 */
	public static String getLocalizedString(HttpServletRequest request, String key) {
		HttpSession session = request.getSession(false);
		String locale = (String) session.getAttribute("currentLocale");
		log.trace("Current locale is " + locale);

		if (locale == null) {
			return enRB.getString(key);
		}

		switch (locale) {
			case "uk":
				return ukRB.getString(key);
			default:
				return enRB.getString(key);
		}

	}

	/**
	 * Private constructor.
	 */
	private Localizator() {

	}
}
