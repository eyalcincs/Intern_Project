package com.stajprojeleri.library;

import java.security.PrivateKey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.stajprojeleri.library"})
@ComponentScan(basePackages = {"com.stajprojeleri.library"})
@EnableJpaRepositories(basePackages = {"com.stajprojeleri.library"})
public class LibraryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApiApplication.class, args);
		
	}

}
