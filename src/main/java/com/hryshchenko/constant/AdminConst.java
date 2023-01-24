package com.hryshchenko.constant;

/**
 * Class containing list of constants related with site administration.
 * 
 * @author Hryshchenko
 *
 */
public class AdminConst {

	// Constant to switch Database type in DAO
	public static final String DB_TYPE = "MySQL";

	// Below are administrative constants for setting up sending emails.
	// In order SendEmail command to work,
	// you can initialize them with your own credentials

	public static final String ADMIN_EMAIL = "admin_email@gmail.com";
	public static String PASSWORD = "admin_password";
	public static final String HOST = "smtp.gmail.com";
	public static final String PORT = "587";

	/**
	 * Private constructor.
	 */
	private AdminConst() {

	}
}
