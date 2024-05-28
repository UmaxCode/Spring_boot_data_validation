package com.umaxcode.spring.boot.data.validation.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue
    private Long id;

    private String token;

    private Date expires;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;
}
