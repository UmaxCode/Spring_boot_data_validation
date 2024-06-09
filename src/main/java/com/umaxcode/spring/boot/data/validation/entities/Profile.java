package com.umaxcode.spring.boot.data.validation.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue
    private Integer id;

    private String bio;

    @OneToOne
    public User user;
}
