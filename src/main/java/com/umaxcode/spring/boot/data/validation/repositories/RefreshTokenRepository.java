package com.umaxcode.spring.boot.data.validation.repositories;

import com.umaxcode.spring.boot.data.validation.entities.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(String token);
}
