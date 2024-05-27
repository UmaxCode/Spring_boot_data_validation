package com.umaxcode.spring.boot.data.validation.services;

import com.umaxcode.spring.boot.data.validation.dtos.requests.PostCreationUpdateDTO;
import com.umaxcode.spring.boot.data.validation.entities.Post;
import com.umaxcode.spring.boot.data.validation.exceptions.custom.PostException;
import com.umaxcode.spring.boot.data.validation.repositories.PostRepository;
import com.umaxcode.spring.boot.data.validation.validations.FieldsValidations;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final FieldsValidations<PostCreationUpdateDTO> fieldsValidations;

    @Transactional
    public Post createPost(PostCreationUpdateDTO postRequest) {

        fieldsValidations.validate(postRequest); // field validation

        Post post = Post.builder()
                .id(UUID.randomUUID().toString())
                .title(postRequest.title())
                .description(postRequest.description())
                .build();

        return postRepository.save(post);
    }

    public List<Post> listPosts() {
        return postRepository.findAll();
    }

    public Post updatePost(String id, PostCreationUpdateDTO update) {

        var post = getPost(id);

        post.setTitle(update.title() != null ? update.title() : post.getTitle());
        post.setDescription(update.description() != null ? update.description() : post.getDescription());

        return postRepository.save(post);
    }

    public Post getPost(String id) {

        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            throw new PostException(HttpStatus.BAD_REQUEST, String.format("A post with the Id = %s does not exist.", id));
        }

        return optionalPost.get();
    }

    public String destroyPost(String id) {

        var post = getPost(id);

        postRepository.delete(post);

        return String.format("A post with an Id = %s is successfully deleted.", post.getId());
    }

}
