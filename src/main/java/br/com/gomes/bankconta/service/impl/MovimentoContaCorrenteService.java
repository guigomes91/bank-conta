package br.com.gomes.bankconta.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import br.com.gomes.bankconta.dto.movimento.MovimentoOutputDTO;
import br.com.gomes.bankconta.entities.conta.Conta;

@Service
public class MovimentoContaCorrenteService implements Operacao {

	@Override
	public void lancarMovimento(MovimentoInputDTO movimento) {
		
	}

	@Override
	public List<MovimentoOutputDTO> consultarMovimento(Conta conta) {
		return null;
	}

}
