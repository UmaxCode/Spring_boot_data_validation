package com.umaxcode.spring.boot.data.validation.repositories;

import com.umaxcode.spring.boot.data.validation.entities.Profile;
import org.springframework.data.repository.ListCrudRepository;


public interface ProfileRepository extends ListCrudRepository<Profile, Long> {

}
