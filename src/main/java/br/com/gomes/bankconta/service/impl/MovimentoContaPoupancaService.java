package br.com.gomes.bankconta.service.impl;

import java.util.List;

import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import br.com.gomes.bankconta.dto.movimento.MovimentoOutputDTO;
import br.com.gomes.bankconta.entities.conta.Conta;

public class MovimentoContaPoupancaService implements Movimento {

	@Override
	public void lancarMovimento(MovimentoInputDTO movimento) {

	}

	@Override
	public List<MovimentoOutputDTO> consultarMovimento(Conta conta) {
		return null;
	}

}
