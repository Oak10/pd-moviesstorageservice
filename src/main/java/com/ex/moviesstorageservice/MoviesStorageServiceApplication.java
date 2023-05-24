package com.ex.moviesstorageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoviesStorageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviesStorageServiceApplication.class, args);
		System.out.println(" --- Starting MoviesStorage Application ---");
	}

}
