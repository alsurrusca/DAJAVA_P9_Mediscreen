package com.openclassrooms.mediscreen.Projet9;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Projet9Application {

	public static void main(String[] args) {
		SpringApplication.run(Projet9Application.class, args);
	}

}
