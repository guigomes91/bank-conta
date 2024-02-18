package br.com.gomes.bankconta.amqp.transacao;

import br.com.gomes.bankconta.utils.AMQPConstantes;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Qualifier("transacaoContaCorrente")
public class MovimentoContaCorrenteAMQPConfiguracao {


	@Bean("queueTransacaoContaCorrente")
	Queue queue() {
		return new Queue(AMQPConstantes.QUEUE_NAME_CLIENT_TRANSACTION, false);
	}

	@Bean("exchangeTransacaoContaCorrente")
	TopicExchange exchange() {
		return new TopicExchange(AMQPConstantes.TOPIC_EXCHANGE_NAME);
	}

	@Bean("bindingTransacaoContaCorrente")
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(AMQPConstantes.ROUTING_KEY_TRANSACTION);
	}

	@Bean("containerTransacaoContaCorrente")
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(AMQPConstantes.QUEUE_NAME_NOTIFICATION);
		return container;
	}
	
	@Bean("rabbitTemplateTransacaoContaCorrente")
	public RabbitTemplate rabbitTemplate(
			ConnectionFactory connectionFactory,
			Jackson2JsonMessageConverter messageConverter) {
		
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter);
		return rabbitTemplate;
	}
	
	@Bean("messageConverterTransacaoContaCorrente")
	public Jackson2JsonMessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	} 
}
