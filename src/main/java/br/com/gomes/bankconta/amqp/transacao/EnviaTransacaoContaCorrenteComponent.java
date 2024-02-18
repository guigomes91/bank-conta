package br.com.gomes.bankconta.amqp.transacao;

import br.com.gomes.bankconta.dto.cliente.ClienteDTO;
import br.com.gomes.bankconta.dto.cliente.EmailClienteInput;
import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import br.com.gomes.bankconta.utils.AMQPConstantes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EnviaTransacaoContaCorrenteComponent {

	private final RabbitTemplate rabbitTemplate;

	public EnviaTransacaoContaCorrenteComponent(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void publicarTransacao(MovimentoInputDTO movimento) {
		log.info("Sending transaction with document: {}", movimento.getNumeroDocumento());

		rabbitTemplate.convertAndSend(
				AMQPConstantes.TOPIC_EXCHANGE_NAME,
				AMQPConstantes.ROUTING_KEY_TRANSACTION,
				movimento);
	}
}
