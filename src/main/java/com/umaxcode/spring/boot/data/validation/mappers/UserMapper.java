package com.umaxcode.spring.boot.data.validation.mappers;

import com.umaxcode.spring.boot.data.validation.entities.User;
import com.umaxcode.spring.boot.data.validation.entities.UserDetailsImpl.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDetails toUserDetails(User user) {
        return new UserDetailsImpl(user.getRole(), user.getPassword(), user.getEmail());
    }
}
