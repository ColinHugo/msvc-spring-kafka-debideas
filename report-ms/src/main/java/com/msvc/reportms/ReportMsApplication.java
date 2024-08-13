package com.msvc.reportms;

import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@AllArgsConstructor
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ReportMsApplication {

	private final EurekaClient eurekaClient;

	public static void main( String[] args ) {
		SpringApplication.run(ReportMsApplication.class, args);
	}
}