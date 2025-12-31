package com.tingeso.tingesoMS_fee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TingesoMsFeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TingesoMsFeeApplication.class, args);
	}

}
