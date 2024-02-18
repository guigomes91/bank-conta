package br.com.gomes.bankconta.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AMQPConstantes {

	public static final String TOPIC_EXCHANGE_NAME_NOTIFICATION = "gomes-bank-email";

	public static final String QUEUE_NAME_NOTIFICATION = "notifications";

	//AMQP bank-client-transaction
	public static final String TOPIC_EXCHANGE_NAME = "gomes-bank-ex";

	public static final String QUEUE_NAME_CLIENT_TRANSACTION = "bank-client-transaction";

	public static final String ROUTING_KEY_TRANSACTION = "bank.client.transactioncontacorrente";
}
