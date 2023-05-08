package com.hryshchenko.command.util;

import com.hryshchenko.command.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Util class containing tools for working with dates.
 * 
 * @author Hryshchenko
 *
 */
public class DateUtil {
	private static final Logger log = LogManager.getLogger(DateUtil.class);

	/**
	 * Getting current date.
	 * 
	 * @return current date.
	 * @throws AppException
	 */
	public static Date getCurrentDate() throws AppException {
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateString = dateFormat.format(currentDate);
		try {
			currentDate = dateFormat.parse(currentDateString);
		} catch (ParseException e) {
			log.error("Failed to get current date. " + e + ".");
			throw new AppException("date_util.failure", e);
		}
		log.debug("Current date is received.");
		return currentDate;
	}

	/**
	 * Getting current date as LocalDate.
	 * 
	 * @return LocalDate instance of current date.
	 */
	public static LocalDate getCurrentLocalDate() {
		log.info("Current date is received in LocalDate format.");
		return LocalDate.now();
	}

	/**
	 * Private constructor.
	 */
	private DateUtil() {

	}
}
