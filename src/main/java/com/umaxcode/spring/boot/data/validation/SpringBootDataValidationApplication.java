package com.umaxcode.spring.boot.data.validation;

import com.umaxcode.spring.boot.data.validation.entities.User;
import com.umaxcode.spring.boot.data.validation.enums.Role;
import com.umaxcode.spring.boot.data.validation.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@SpringBootApplication
public class SpringBootDataValidationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDataValidationApplication.class, args);
	}

//
//	@Bean
//	public CommandLineRunner commandLineRunner(UserService userService, PasswordEncoder passwordEncoder) {
//		return args -> {
//			var user1  = User.builder()
//					.id(UUID.randomUUID().toString())
//					.email("kofi@gmail.com")
//					.role(Role.MANAGER)
//					.isActive(true)
//					.username("kofi")
//					.password(passwordEncoder.encode("kofi"))
//					.firstName("Kofi")
//					.build();
//
//			var user2 = User.builder()
//					.id(UUID.randomUUID().toString())
//					.email("kwame@gmail.com")
//					.role(Role.ADMIN)
//					.isActive(true)
//					.username("kwame")
//					.password(passwordEncoder.encode("kwame"))
//					.firstName("Kwame")
//					.build();
//
//			var user3 = User.builder()
//					.id(UUID.randomUUID().toString())
//					.email("maxwell@gmail.com")
//					.role(Role.USER)
//					.isActive(true)
//					.username("maxwell")
//					.password(passwordEncoder.encode("maxwell"))
//					.firstName("Maxwell")
//					.build();
//
//
//			userService.addUser(user1);
//			userService.addUser(user2);
//			userService.addUser(user3);
//		};
//	}

}
