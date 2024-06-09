package com.umaxcode.spring.boot.data.validation.eventListeners;

import com.umaxcode.spring.boot.data.validation.entities.Profile;
import com.umaxcode.spring.boot.data.validation.entities.User;
import jakarta.persistence.PostPersist;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CrudEventListener {


    @PostPersist
    public void PostPersist(User entity) {

        var profile = Profile.builder()
                .bio("This profile be longs to ")
                .user(entity)
                .build();


        log.info("The info of a user is: {}", profile);

    }
}
