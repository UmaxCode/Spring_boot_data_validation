package com.umaxcode.spring.boot.data.validation.repositories;

import com.umaxcode.spring.boot.data.validation.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, String> {
}
