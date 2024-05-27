package com.umaxcode.spring.boot.data.validation.repositories;

import com.umaxcode.spring.boot.data.validation.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
}
