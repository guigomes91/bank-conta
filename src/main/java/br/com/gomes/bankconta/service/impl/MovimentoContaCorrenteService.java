package br.com.gomes.bankconta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import br.com.gomes.bankconta.dto.movimento.MovimentoOutputDTO;
import br.com.gomes.bankconta.entities.conta.Conta;
import br.com.gomes.bankconta.entities.movimento.MovimentoContaCorrenteEntity;
import br.com.gomes.bankconta.repository.MovimentoContaCorrenteRepository;

@Service
public class MovimentoContaCorrenteService implements Movimento {
	
	@Autowired
	private MovimentoContaCorrenteRepository movRepository;

	@Override
	public void lancarMovimento(MovimentoInputDTO movimento) {
		MovimentoContaCorrenteEntity entity = new MovimentoContaCorrenteEntity().dtoToEntity(movimento);
		
		movRepository.save(entity);
	}

	@Override
	public List<MovimentoOutputDTO> consultarMovimento(Conta conta) {
		return null;
	}

}
