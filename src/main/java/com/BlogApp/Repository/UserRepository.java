package com.BlogApp.Repository;

import com.BlogApp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByUsername(String Username);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    

 
}
