package com.hryshchenko.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandContainerTest {

    @Test
    void testGetCommand() {
        Command courseInfoCommand = CommandContainer.getCommand("course_info");
        assertTrue(courseInfoCommand instanceof CourseInfoCommand);

        Command createCourseOpenCommand = CommandContainer.getCommand("create_course_open");
        assertTrue(createCourseOpenCommand instanceof CreateCourseOpenCommand);

        Command createCourseSaveCommand = CommandContainer.getCommand("create_course_save");
        assertTrue(createCourseSaveCommand instanceof CreateCourseSaveCommand);

        Command editCourseOpenCommand = CommandContainer.getCommand("edit_course_open");
        assertTrue(editCourseOpenCommand instanceof EditCourseOpenCommand);

        Command editCourseSaveCommand = CommandContainer.getCommand("edit_course_save");
        assertTrue(editCourseSaveCommand instanceof EditCourseSaveCommand);

        Command editUserOpenCommand = CommandContainer.getCommand("edit_user_open");
        assertTrue(editUserOpenCommand instanceof EditUserOpenCommand);

        Command editUserSaveCommand = CommandContainer.getCommand("edit_user_save");
        assertTrue(editUserSaveCommand instanceof EditUserSaveCommand);

        Command getAllCoursesCommand = CommandContainer.getCommand("get_all_courses");
        assertTrue(getAllCoursesCommand instanceof GetAllCoursesCommand);

        Command getAllUsersCommand = CommandContainer.getCommand("get_all_users");
        assertTrue(getAllUsersCommand instanceof GetAllUsersCommand);

        Command loginCommand = CommandContainer.getCommand("login");
        assertTrue(loginCommand instanceof LoginCommand);

        Command logoutCommand = CommandContainer.getCommand("logout");
        assertTrue(logoutCommand instanceof LogoutCommand);

        Command markOpenCommand = CommandContainer.getCommand("mark_open");
        assertTrue(markOpenCommand instanceof MarkOpenCommand);

        Command markSaveCommand = CommandContainer.getCommand("mark_save");
        assertTrue(markSaveCommand instanceof MarkSaveCommand);

        Command myProfileCommand = CommandContainer.getCommand("my_profile");
        assertTrue(myProfileCommand instanceof MyProfileCommand);

        Command registerCommand = CommandContainer.getCommand("register");
        assertTrue(registerCommand instanceof RegisterCommand);

        Command removeCourseCommand = CommandContainer.getCommand("remove_course");
        assertTrue(removeCourseCommand instanceof RemoveCourseCommand);

        Command subscribeCommand = CommandContainer.getCommand("subscribe");
        assertTrue(subscribeCommand instanceof SubscribeCommand);

        Command writeAdminCommand = CommandContainer.getCommand("write_admin");
        assertTrue(writeAdminCommand instanceof WriteAdminCommand);
    }

}
