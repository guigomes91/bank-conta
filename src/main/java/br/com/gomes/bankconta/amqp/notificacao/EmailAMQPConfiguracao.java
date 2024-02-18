package br.com.gomes.bankconta.amqp.notificacao;

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
import org.springframework.context.annotation.Primary;

@Primary
@Configuration
@Qualifier("emailNotificacao")
public class EmailAMQPConfiguracao {
	@Bean("queueNotificacaoEmail")
	@Primary
	Queue queue() {
		return new Queue(AMQPConstantes.QUEUE_NAME_NOTIFICATION, false);
	}

	@Bean("exchangeNotificao")
	@Primary
	TopicExchange exchange() {
		return new TopicExchange(AMQPConstantes.TOPIC_EXCHANGE_NAME_NOTIFICATION);
	}

	@Bean("bindingEmail")
	@Primary
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("gomes.bank.#");
	}

	@Bean("containerEmail")
	@Primary
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(AMQPConstantes.QUEUE_NAME_NOTIFICATION);
		return container;
	}
	
	@Bean("rabbitTemplateEmail")
	@Primary
	public RabbitTemplate rabbitTemplate(
			ConnectionFactory connectionFactory,
			Jackson2JsonMessageConverter messageConverter) {
		
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter);
		return rabbitTemplate;
	}
	
	@Bean("messageConverterEmail")
	@Primary
	public Jackson2JsonMessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	} 
}
