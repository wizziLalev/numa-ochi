package com.numaochi.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for user registration requests.
 * Contains the username and password provided by the user during registration.
 */
public class UserRegistrationDTO {
    private String username;

    @Size(min = 6, max = 12, message = "Password must be between 6 and 12 characters long.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$&*]).*$", message = "Password must contain at least one capital letter and one special character.")
    private String password;

    /**
     * Returns the username for registration.
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for registration.
     * @param username the username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password for registration.
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for registration.
     * @param password the password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
