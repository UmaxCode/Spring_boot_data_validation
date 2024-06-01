package com.umaxcode.spring.boot.data.validation.controllers;

import com.umaxcode.spring.boot.data.validation.dtos.requests.PostCreationUpdateDTO;
import com.umaxcode.spring.boot.data.validation.dtos.responses.SuccessMessage;
import com.umaxcode.spring.boot.data.validation.exceptions.custom.PostException;
import com.umaxcode.spring.boot.data.validation.services.PostService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Tag(name = "User Post",
description = "Post controller for allowing users to post data")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PostCreationUpdateDTO post) {

        var message = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }


    @GetMapping
    public ResponseEntity<?> list() {

        var posts = postService.listPosts();
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") String id, @RequestBody PostCreationUpdateDTO update) {

        var post = postService.updatePost(id, update);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieve(@PathVariable(name = "id") String id) {

        var post = postService.getPost(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") String id) {

        var message = postService.destroyPost(id);
        return ResponseEntity.ok(new SuccessMessage(message));
    }

    @ExceptionHandler(PostException.class)
    public ResponseEntity<?> postExceptionHandler(PostException error) {

        Map<String, String> response = new HashMap<>();
        response.put("error", error.getMessage());
        return ResponseEntity.status(error.getStatus()).body(response);
    }


}
