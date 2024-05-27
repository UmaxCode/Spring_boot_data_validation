package com.umaxcode.spring.boot.data.validation.entities;


import com.umaxcode.spring.boot.data.validation.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "_user")
public class User {

    @Id
    private String id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    @Email(message = "invalid email")
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private Boolean isActive;

    private Role role;


    public Map<String, String> getUserDetails() {
        HashMap<String, String> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("email", email);
        userData.put("role", role.name());
        return userData;
    }

}
