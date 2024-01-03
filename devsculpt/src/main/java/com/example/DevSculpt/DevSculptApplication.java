package com.example.DevSculpt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example")
public class DevSculptApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevSculptApplication.class, args);
	}

}
