package com.solactive.price.pricingengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PricingengineApplication {

	public static void main(String[] args) {
		SpringApplication.run(PricingengineApplication.class, args);
	}

}
