package com.hryshchenko.dao.entity;

import com.hryshchenko.entity.Role;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.Assert.assertEquals;

public class RoleTest {

    @ParameterizedTest
    @CsvSource({"ADMIN, 0", "TEACHER, 1", "STUDENT, 2"})
    void testGetValue(String roleString, String value) {
        Role role = Role.valueOf(roleString);
        int number = Integer.parseInt(value);
        assertEquals(number, role.getValue());
    }

}
