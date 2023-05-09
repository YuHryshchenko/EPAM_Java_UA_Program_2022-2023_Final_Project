package com.hryshchenko.entity;

/**
 * Enumeration of roles.
 * 
 * @author Hryshchenko
 *
 */
public enum Role {
	ADMIN(0), TEACHER(1), STUDENT(2);

	private final int value;

	Role(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

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
