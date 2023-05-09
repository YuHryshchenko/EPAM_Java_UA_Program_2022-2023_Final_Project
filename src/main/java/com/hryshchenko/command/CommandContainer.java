package com.hryshchenko.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Class containing all commands.
 * 
 * @author Hryshchenko
 *
 */
public class CommandContainer {
	private static final Logger log = LogManager.getLogger(CommandContainer.class);
	private static final Map<String, Command> COMMANDS;

	static {
		COMMANDS = new HashMap<>();
		COMMANDS.put("course_info", new CourseInfoCommand());
		COMMANDS.put("create_course_open", new CreateCourseOpenCommand());
		COMMANDS.put("create_course_save", new CreateCourseSaveCommand());
		COMMANDS.put("edit_course_open", new EditCourseOpenCommand());
		COMMANDS.put("edit_course_save", new EditCourseSaveCommand());
		COMMANDS.put("edit_user_open", new EditUserOpenCommand());
		COMMANDS.put("edit_user_save", new EditUserSaveCommand());
		COMMANDS.put("get_all_courses", new GetAllCoursesCommand());
		COMMANDS.put("get_all_users", new GetAllUsersCommand());
		COMMANDS.put("login", new LoginCommand());
		COMMANDS.put("logout", new LogoutCommand());
		COMMANDS.put("mark_open", new MarkOpenCommand());
		COMMANDS.put("mark_save", new MarkSaveCommand());
		COMMANDS.put("my_profile", new MyProfileCommand());
		COMMANDS.put("register", new RegisterCommand());
		COMMANDS.put("remove_course", new RemoveCourseCommand());
		COMMANDS.put("subscribe", new SubscribeCommand());
		COMMANDS.put("write_admin", new WriteAdminCommand());
		log.debug("CommandContainer was initialized successfully.");
		log.trace("Amount of commands: " + COMMANDS.size());
	}

	/**
	 * Getting command object by its name.
	 * 
	 * @param commandName
	 * @return An object of command.
	 */
	public static Command getCommand(String commandName) {
		return COMMANDS.get(commandName);
	}

}
