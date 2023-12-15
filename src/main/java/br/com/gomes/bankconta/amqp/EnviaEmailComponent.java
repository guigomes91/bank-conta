package br.com.gomes.bankconta.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import br.com.gomes.bankconta.dto.cliente.EmailClienteInput;
import br.com.gomes.bankconta.utils.AMQPConstantes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EnviaEmailComponent {

	private final RabbitTemplate rabbitTemplate;

	public EnviaEmailComponent(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void enviarEmail(String email) {
		log.info("Sending message with email {}", email);
		rabbitTemplate.convertAndSend(AMQPConstantes.TOPIC_EXCHANGE_NAME, "gomes.bank.email", new EmailClienteInput(email));
	}
}
