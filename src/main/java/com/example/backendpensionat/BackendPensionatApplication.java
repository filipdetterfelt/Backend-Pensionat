package com.example.backendpensionat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class BackendPensionatApplication {

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
		}else if (Objects.equals(args[0], "ReadEventQueue")) {
			SpringApplication application = new SpringApplication(ReadEventQueue.class);
			application.setWebApplicationType(WebApplicationType.NONE);
			application.run(args);
		}
	}
}
