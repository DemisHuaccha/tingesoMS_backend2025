package com.tingeso.tingesoMS_inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TingesoMsInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(TingesoMsInventoryApplication.class, args);
	}

}
