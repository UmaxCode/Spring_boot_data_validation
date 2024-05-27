package com.umaxcode.spring.boot.data.validation.services;


import com.umaxcode.spring.boot.data.validation.entities.User;
import com.umaxcode.spring.boot.data.validation.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User getUserByEmail(String email){

        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent()){
            return user.get();
        }

        throw new RuntimeException("User not found");
    }

    public User addUser(User user){
        return userRepository.save(user);
    }
}
