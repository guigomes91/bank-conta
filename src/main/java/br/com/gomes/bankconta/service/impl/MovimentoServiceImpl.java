package br.com.gomes.bankconta.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import br.com.gomes.bankconta.dto.movimento.MovimentoOutputDTO;
import br.com.gomes.bankconta.entities.conta.Conta;
import br.com.gomes.bankconta.enums.TipoConta;

@Service
public class MovimentoServiceImpl {
	
	@Autowired
	private List<Operacao> operacoes;

	@Transactional
	public MovimentoOutputDTO lancarMovimento(TipoConta tipoConta, MovimentoInputDTO movimento) {
		Operacao operacao = carregarOperacoes().get(tipoConta);
		return operacao.lancarMovimento(movimento);
	}
	
	@Transactional(readOnly = true)
	public void consultarMovimento(Operacao operacao, Conta conta) {
		operacao.consultarMovimento(conta);
	}
	
	private Map<TipoConta, Operacao> carregarOperacoes() {
		Map<TipoConta, Operacao> tipoMovimentos = new HashMap<>();
		
		operacoes.forEach(op -> {
			tipoMovimentos.put(op.getTipoOperacao(), op.getInstance());
		});
		
		return tipoMovimentos;
	}
}
