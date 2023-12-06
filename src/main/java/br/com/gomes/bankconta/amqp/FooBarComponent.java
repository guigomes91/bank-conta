package br.com.gomes.bankconta.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class FooBarComponent {

	static final String topicExchangeName = "gomes-bank-email";

	private final RabbitTemplate rabbitTemplate;

	public FooBarComponent(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void enviarEmail(String email) throws Exception {
		System.out.println("Sending message with email " + email + "...");
		
		rabbitTemplate.convertAndSend(topicExchangeName, "gomes.bank.email", email);
	}
}
