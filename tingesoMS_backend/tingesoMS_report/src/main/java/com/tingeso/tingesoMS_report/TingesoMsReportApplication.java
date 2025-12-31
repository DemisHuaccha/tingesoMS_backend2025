package com.tingeso.tingesoMS_report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TingesoMsReportApplication {
	public static void main(String[] args) {
		SpringApplication.run(TingesoMsReportApplication.class, args);
	}

	@org.springframework.context.annotation.Bean
	@org.springframework.cloud.client.loadbalancer.LoadBalanced
	public org.springframework.web.client.RestTemplate restTemplate() {
		return new org.springframework.web.client.RestTemplate();
	}
}
