package br.com.gomes.bankconta.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import br.com.gomes.bankconta.dto.movimento.MovimentoOutputDTO;
import br.com.gomes.bankconta.entities.conta.Conta;
import br.com.gomes.bankconta.enums.TipoConta;

@Service
public class MovimentoServiceImpl {
	
	private Map<TipoConta, Operacao> tipoMovimentos = new HashMap<>();
	
	public MovimentoServiceImpl() {
		tipoMovimentos.put(TipoConta.CC, new MovimentoContaCorrenteService());
		tipoMovimentos.put(TipoConta.CP, new MovimentoContaPoupancaService());
	}

	public MovimentoOutputDTO lancarMovimento(TipoConta tp, MovimentoInputDTO movimento) {
		Operacao operacao = tipoMovimentos.get(tp);
		return operacao.lancarMovimento(movimento);
	}
	
	public void consultarMovimento(Operacao operacao, Conta conta) {
		operacao.consultarMovimento(conta);
	}
}
