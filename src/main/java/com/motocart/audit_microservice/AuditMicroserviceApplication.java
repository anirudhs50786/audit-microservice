package com.motocart.audit_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AuditMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditMicroserviceApplication.class, args);
	}

}
