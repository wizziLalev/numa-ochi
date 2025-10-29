package com.numaochi.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for managing {@link User} entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for.
     * @return an {@link Optional} containing the user if found, or empty otherwise.
     */
    Optional<User> findByUsername(String username);
}
