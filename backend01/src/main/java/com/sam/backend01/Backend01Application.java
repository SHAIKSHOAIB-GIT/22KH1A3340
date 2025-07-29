package com.sam.backend01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Backend01Application {

	public static void main(String[] args) {
		SpringApplication.run(Backend01Application.class, args);
	}
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
	}

}
