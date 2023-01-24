package com.hryshchenko.entity;

/**
 * Enumeration of roles.
 * 
 * @author Hryshchenko
 *
 */
public enum Role {
	ADMIN, TEACHER, STUDENT;

	/**
	 * Getting role of given user.
	 * 
	 * @param user
	 * @return Role of given user.
	 */
	public static Role getRole(User user) {
		int roleId = user.getRoleId();
		return Role.values()[roleId];
	}

	/**
	 * Getting name of given role.
	 * 
	 * @return Name of role.
	 */
	public String getName() {
		return name().toLowerCase();
	}
}
