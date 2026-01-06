package com.tingeso.tingesoMS_report.Dtos;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
