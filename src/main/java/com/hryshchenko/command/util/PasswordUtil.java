package com.hryshchenko.command.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Util class containing tools for working with passwords.
 * 
 * @author Hryshchenko
 *
 */
public class PasswordUtil {
	private static final Logger log = LogManager.getLogger(PasswordUtil.class);

	/**
	 * Getting hashed and salted password.
	 * 
	 * @param password
	 * @param salt
	 * @return Hashed salted password.
	 */
	public static String hashSaltedPassword(String password, String salt) {
		String saltedPassword = password + salt;
		log.debug("Received salted and hashed password.");
		return DigestUtils.sha256Hex(saltedPassword);
	}

	/**
	 * Private constructor.
	 */
	private PasswordUtil() {

	}
}
