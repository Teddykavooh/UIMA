package com.teddykavooh.uima.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    // Verifies that the User model retains and returns the correct data.
    public void userModelShouldRetainAndReturnCorrectData() {
        User user = new User("John", "Doe", "john.doe@example.com", "password123");

        assertEquals("John", user.getFirstname());
        assertEquals("Doe", user.getLastname());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
    }
}
