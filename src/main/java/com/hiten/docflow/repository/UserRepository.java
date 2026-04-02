package com.hiten.docflow.repository;

import com.hiten.docflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository — finds users in the database
 *
 * Optional<User> means: "the result might exist or might not"
 * It's safer than returning null — prevents NullPointerExceptions.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
