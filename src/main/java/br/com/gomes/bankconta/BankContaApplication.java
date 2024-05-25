package br.com.gomes.bankconta;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class BankContaApplication {

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/bank-conta");
		SpringApplication.run(BankContaApplication.class, args);
		log.info("## Started app ##");
	}
}
