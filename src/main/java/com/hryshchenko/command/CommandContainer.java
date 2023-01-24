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
		COMMANDS.put("courseinfo", new CourseInfoCommand());
		COMMANDS.put("createcourseopen", new CreateCourseOpenCommand());
		COMMANDS.put("createcoursesave", new CreateCourseSaveCommand());
		COMMANDS.put("editcourseopen", new EditCourseOpenCommand());
		COMMANDS.put("editcoursesave", new EditCourseSaveCommand());
		COMMANDS.put("edituseropen", new EditUserOpenCommand());
		COMMANDS.put("editusersave", new EditUserSaveCommand());
		COMMANDS.put("getallcourses", new GetAllCoursesCommand());
		COMMANDS.put("getallusers", new GetAllUsersCommand());
		COMMANDS.put("login", new LoginCommand());
		COMMANDS.put("logout", new LogoutCommand());
		COMMANDS.put("markopen", new MarkOpenCommand());
		COMMANDS.put("marksave", new MarkSaveCommand());
		COMMANDS.put("myprofile", new MyProfileCommand());
		COMMANDS.put("register", new RegisterCommand());
		COMMANDS.put("removecourse", new RemoveCourseCommand());
		COMMANDS.put("subscribe", new SubscribeCommand());
		COMMANDS.put("writeadmin", new WriteAdminCommand());
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
