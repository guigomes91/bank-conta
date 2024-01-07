package br.com.gomes.bankconta.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import br.com.gomes.bankconta.dto.movimento.MovimentoOutputDTO;
import br.com.gomes.bankconta.entities.conta.Conta;

@Service
public class MovimentoContaPoupancaService implements Operacao {

	@Override
	public MovimentoOutputDTO lancarMovimento(MovimentoInputDTO movimento) {
		return null;
	}

	@Override
	public List<MovimentoOutputDTO> consultarMovimento(Conta conta) {
		return null;
	}

}
