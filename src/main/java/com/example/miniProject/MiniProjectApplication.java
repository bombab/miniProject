package com.example.miniProject;

import com.example.miniProject.domain.Board;
import com.example.miniProject.repository.BoardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MiniProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniProjectApplication.class, args);
	}

	//	@Bean
	public CommandLineRunner run(BoardRepository repository){
		return args -> {
			repository.findAll().forEach(System.out::println);

		};

	}
}