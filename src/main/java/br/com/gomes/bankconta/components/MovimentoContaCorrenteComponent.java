package br.com.gomes.bankconta.components;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import br.com.gomes.bankconta.dto.movimento.MovimentoOutputDTO;
import br.com.gomes.bankconta.entities.conta.Conta;
import br.com.gomes.bankconta.entities.conta.ContaCorrenteEntity;
import br.com.gomes.bankconta.entities.movimento.MovimentoContaCorrenteEntity;
import br.com.gomes.bankconta.repository.MovimentoContaCorrenteRepository;
import br.com.gomes.bankconta.service.impl.Operacao;
import br.com.gomes.bankconta.validators.ContaCorrenteValidator;
import br.com.gomes.bankconta.validators.SaldoContaCorrenteValidator;

@Component
public class MovimentoContaCorrenteComponent implements Operacao {
	
	@Autowired
	private MovimentoContaCorrenteRepository movRepository;
	
	@Autowired
	private ContaCorrenteValidator ccValidator;
	
	@Autowired
	private SaldoContaCorrenteValidator saldoValidator;

	@Override
	public MovimentoOutputDTO lancarMovimento(MovimentoInputDTO movimento) {
		ContaCorrenteEntity contaCorrenteEntity = ccValidator.contaExistente(movimento.getConta().getId());
		MovimentoContaCorrenteEntity entity = MovimentoContaCorrenteEntity.dtoToEntity(movimento);
		
		MovimentoOutputDTO movimentoOutputDTO = MovimentoOutputDTO.entityToDto(movRepository.save(entity));
		
		saldoValidator.tratarSaldoContaCorrente(contaCorrenteEntity, movimento);		
		
		return movimentoOutputDTO;
	}

	@Override
	public List<MovimentoOutputDTO> consultarMovimento(Conta conta) {
		return MovimentoContaCorrenteEntity.listEntityToListDTO(movRepository.findAll());
	}

}
