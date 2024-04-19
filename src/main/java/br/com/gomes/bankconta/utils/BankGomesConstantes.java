package br.com.gomes.bankconta.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BankGomesConstantes {

	//Dados Conta Corrente
	public static final int NUMERO_AGENCIA = 2023;
	public static final int MIN_CONTAS = 1;
	public static final int MAX_CONTAS = 200000000;
	public static final int VARIACAO_POUPANCA = 2024;

	//Dados de envio por e-mail ao criar conta no banco
	public static final String ASSUNTO_CRIACAO_CONTA = "Conta corrente criada em Gomes Bank";
	public static final String MENSAGEM_CRIACAO_CONTA = "Parabéns, você acaba de adquirir uma vida sem complicações!";

	//Dados de envio por e-mail ao realizar transferência
	public static final String ASSUNTO_TRANSFERENCIA_ENTRE_CONTAS = "Transferência recebida";
	public static final String MENSAGEM_TRANSFERENCIA_ENTRE_CONTAS = "Você acaba de receber uma transferência de %s";
	public static final String TRANSFERENCIA_CONCLUIDA = "Transferência realizada com sucesso!";
}
