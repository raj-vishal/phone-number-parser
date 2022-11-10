package com.doordash.interview.phone_number_parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@Slf4j
@ComponentScan(basePackages={"com.doordash.interview.phone_number_parser.*"})
@EntityScan(basePackages = {"com.doordash.interview.phone_number_parser.model"})  // scan JPA entities
public class PhoneNumberParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhoneNumberParserApplication.class, args);
	}

}
