package com.nt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DataCollectionServiceApplication {
@Bean	
  public WebClient createWebClient() {
	 return WebClient.create() ;
  }
	public static void main(String[] args) {
		SpringApplication.run(DataCollectionServiceApplication.class, args);
	}

}
