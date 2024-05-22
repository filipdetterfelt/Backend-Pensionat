package com.example.backendpensionat;

import com.example.backendpensionat.Security.UserDataSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

@SpringBootApplication
public class BackendPensionatApplication {
	@Autowired
	private UserDataSeeder userDataSeeder;

	public static void main(String[] args) {
		if (args.length == 0) {
			SpringApplication.run(BackendPensionatApplication.class, args);
		} else if (Objects.equals(args[0], "SyncContractCustomers")) {
			SpringApplication application = new SpringApplication(SyncContractCustomers.class);
			application.setWebApplicationType(WebApplicationType.NONE);
			application.run(args);
		} else if (Objects.equals(args[0], "SyncShippers")) {
			SpringApplication application = new SpringApplication(SyncShippers.class);
			application.setWebApplicationType(WebApplicationType.NONE);
			application.run(args);
		} else if (Objects.equals(args[0], "SyncMockData")) {
			SpringApplication application = new SpringApplication(SyncMockData.class);
			application.setWebApplicationType(WebApplicationType.NONE);
			application.run(args);
		} else if (Objects.equals(args[0], "ReadEventQueue")) {
			SpringApplication application = new SpringApplication(ReadEventQueue.class);
			application.setWebApplicationType(WebApplicationType.NONE);
			application.run(args);
		}
	}

	@Bean
	CommandLineRunner commandLineRunner() {
		return args -> {
			userDataSeeder.Seed();
		};
	}
}
