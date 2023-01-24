package com.hryshchenko.command.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.SecureRandom;

/**
 * Util class containing tools for generation salt.
 * 
 * @author Hryshchenko
 *
 */
public class SaltGenerator {
	private static final Logger log = LogManager.getLogger(SaltGenerator.class);
	public static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	/**
	 * Method which generates random salt containing 6 symbols.
	 * 
	 * @return random string.
	 */
	public static String generateSalt() {
		StringBuilder sb = new StringBuilder();
		SecureRandom sr = new SecureRandom();
		char symbol = '0';
		int index = 0;
		while (sb.length() < 6) {
			index = sr.nextInt(ALPHABET.length());
			symbol = ALPHABET.charAt(index);
			sb.append(symbol);
		}

		log.debug("Salt is generated successfully.");
		return sb.toString();
	}

	/**
	 * Private constructor.
	 */
	private SaltGenerator() {

	}
}
