package com.restarea.account;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@SpringBootApplication
public class AccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

}

@Component
class AppRunner implements ApplicationRunner {
	private final Environment env;

	public AppRunner(Environment env) {
		this.env = env;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Active profiles : " + Arrays.toString(env.getActiveProfiles()));
		System.out.println("resource.response-path : " + env.getProperty("resource.response-path"));
	}

}