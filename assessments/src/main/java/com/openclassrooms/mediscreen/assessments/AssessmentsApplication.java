package com.openclassrooms.mediscreen.assessments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients("com.openclassrooms.mediscreen.assessments")
//@ImportAutoConfiguration(FeignAutoConfiguration.class)
public class AssessmentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssessmentsApplication.class, args);
	}

}
