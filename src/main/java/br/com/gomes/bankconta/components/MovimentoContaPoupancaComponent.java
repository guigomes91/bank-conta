package br.com.gomes.bankconta.components;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import br.com.gomes.bankconta.dto.movimento.MovimentoOutputDTO;
import br.com.gomes.bankconta.entities.conta.Conta;
import br.com.gomes.bankconta.entities.conta.ContaPoupancaEntity;
import br.com.gomes.bankconta.entities.movimento.MovimentoContaPoupancaEntity;
import br.com.gomes.bankconta.repository.MovimentoContaPoupancaRepository;
import br.com.gomes.bankconta.service.impl.Operacao;

@Component
public class MovimentoContaPoupancaComponent implements Operacao {

	@Autowired
	private MovimentoContaPoupancaRepository contaPoupancaRepository;
	
	@Override
	public MovimentoOutputDTO lancarMovimento(MovimentoInputDTO movimento) {
		MovimentoContaPoupancaEntity movimentoContaPoupancaEntity = contaPoupancaRepository.save(MovimentoContaPoupancaEntity.dtoToEntity(movimento));
		
		return MovimentoOutputDTO.entityToDto(movimentoContaPoupancaEntity);
	}

	@Override
	public List<MovimentoOutputDTO> consultarMovimento(Conta conta) {
		ContaPoupancaEntity contaPoupancaEntity = null;
		if (conta instanceof ContaPoupancaEntity) {
			contaPoupancaEntity = (ContaPoupancaEntity) conta;
		}
		
		List<MovimentoContaPoupancaEntity> listaMovimentoContaPoupanca = contaPoupancaRepository.findByConta(contaPoupancaEntity, null);
		return MovimentoContaPoupancaEntity.listEntityToList(listaMovimentoContaPoupanca);
	}

}
