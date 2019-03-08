package com.enigma.task.studyperiod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.enigma.task.studyperiod.config.DaoSpringConfig;

@ComponentScan(basePackages={"com.enigma.task.studyperiod"})
@EnableJpaRepositories({"com.enigma.task.studyperiod.repository"})
@EntityScan({"com.enigma.task.studyperiod.model"})
@Import({DaoSpringConfig.class})
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
