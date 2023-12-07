package br.com.gomes.bankconta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankContaApplication {

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/bankconta");
		SpringApplication.run(BankContaApplication.class, args);
	}
}
