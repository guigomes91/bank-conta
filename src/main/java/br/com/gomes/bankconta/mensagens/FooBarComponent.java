package br.com.gomes.bankconta.mensagens;

import javax.sound.midi.Receiver;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class FooBarComponent {
	
	static final String topicExchangeName = "spring-boot-exchange";
	
	 private final RabbitTemplate rabbitTemplate;
	 private final Receiver receiver;
	 
	 public FooBarComponent(Receiver receiver, RabbitTemplate rabbitTemplate) {
		    this.receiver = receiver;
		    this.rabbitTemplate = rabbitTemplate;
		  }
	 
	  public void run() throws Exception {
	    System.out.println("Sending message...");
	    rabbitTemplate.convertAndSend(topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
	    receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
	  }
}
