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
public class MovimentoContaCorrenteService implements Operacao {
	
	@Autowired
	private MovimentoContaCorrenteRepository movRepository;

	@Override
	public MovimentoOutputDTO lancarMovimento(MovimentoInputDTO movimento) {
		MovimentoContaCorrenteEntity entity = MovimentoContaCorrenteEntity.dtoToEntity(movimento);
		
		return MovimentoOutputDTO.entityToDto(movRepository.save(entity));
	}

	@Override
	public List<MovimentoOutputDTO> consultarMovimento(Conta conta) {
		return MovimentoContaCorrenteEntity.listEntityToListDTO(movRepository.findAll());
	}

}
