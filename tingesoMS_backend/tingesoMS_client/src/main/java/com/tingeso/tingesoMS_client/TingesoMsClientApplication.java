package com.tingeso.tingesoMS_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TingesoMsClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(TingesoMsClientApplication.class, args);
	}

}
