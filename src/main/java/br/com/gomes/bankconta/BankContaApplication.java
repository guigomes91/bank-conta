package br.com.gomes.bankconta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BankContaApplication {

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/bank-conta");
		SpringApplication.run(BankContaApplication.class, args);
	}
}
