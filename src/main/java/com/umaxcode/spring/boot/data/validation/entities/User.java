package com.umaxcode.spring.boot.data.validation.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.umaxcode.spring.boot.data.validation.enums.Role;
import jakarta.persistence.*;
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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private RefreshToken refreshToken;


    public Map<String, String> getUserDetails() {
        HashMap<String, String> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("email", email);
        userData.put("role", role.name());
        return userData;
    }

    @Override
    public String toString(){

        return String.format("%s %s %s", username, email, role);
    }

}
