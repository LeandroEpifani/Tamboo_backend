package com.backend.tamboo.repository;

import com.backend.tamboo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    // Se vuoi cercare per email:
    Optional<User> findByEmail(String email);
}

