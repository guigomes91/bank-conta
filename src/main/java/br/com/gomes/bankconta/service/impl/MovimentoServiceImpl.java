package br.com.gomes.bankconta.service.impl;

import org.springframework.stereotype.Service;

import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import br.com.gomes.bankconta.entities.conta.Conta;

@Service
public class MovimentoServiceImpl {

	public void lancarMovimento(Operacao operacao, MovimentoInputDTO movimento) {
		operacao.lancarMovimento(movimento);
	}
	
	public void consultarMovimento(Operacao operacao, Conta conta) {
		operacao.consultarMovimento(conta);
	}
}
