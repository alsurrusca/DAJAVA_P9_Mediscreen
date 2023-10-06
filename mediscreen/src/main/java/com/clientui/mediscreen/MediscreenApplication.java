package com.clientui.mediscreen;

import com.clientui.mediscreen.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableFeignClients("com.clientui.mediscreen")
@EnableSwagger2
@Import(AppConfig.class)
public class MediscreenApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediscreenApplication.class, args);
	}

}
