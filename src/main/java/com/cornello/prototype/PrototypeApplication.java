package com.cornello.prototype;

import com.cornello.prototype.entity.AppUser;
import com.cornello.prototype.entity.Role;
import com.cornello.prototype.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class PrototypeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrototypeApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		final String ROLE_USER = "ROLE_USER";
		return args -> {
			userService.saveRole(new Role(null,ROLE_USER));
			userService.saveRole(new Role(null,"ROLE_MANAGER"));
			userService.saveRole(new Role(null,"ROLE_ADMIN"));
			userService.saveRole(new Role(null,"ROLE_ROOT_ADMIN"));

			userService.saveUser(new AppUser(null,"Miguel Gru","miguel","unknown", new ArrayList<>()));
			userService.saveUser(new AppUser(null,"Rico Henkel","ricoloko","ricerice", new ArrayList<>()));
			userService.saveUser(new AppUser(null,"Paul Omega","cornello","123password", new ArrayList<>()));

			userService.assignRole("miguel",ROLE_USER);

			userService.assignRole("ricoloko",ROLE_USER);
			userService.assignRole("ricoloko","ROLE_MANAGER");

			userService.assignRole("cornello",ROLE_USER);
			userService.assignRole("cornello","ROLE_ADMIN");
		};
	}

}
