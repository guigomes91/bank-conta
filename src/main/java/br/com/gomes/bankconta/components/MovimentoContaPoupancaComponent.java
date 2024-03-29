package br.com.gomes.bankconta.components;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import br.com.gomes.bankconta.dto.movimento.MovimentoOutputDTO;
import br.com.gomes.bankconta.entities.conta.Conta;
import br.com.gomes.bankconta.entities.conta.ContaPoupancaEntity;
import br.com.gomes.bankconta.entities.movimento.MovimentoContaPoupancaEntity;
import br.com.gomes.bankconta.enums.TipoConta;
import br.com.gomes.bankconta.repository.MovimentoContaPoupancaRepository;
import br.com.gomes.bankconta.service.impl.Operacao;

@Component
public class MovimentoContaPoupancaComponent implements Operacao {

	@Autowired
	private MovimentoContaPoupancaRepository contaPoupancaRepository;
	
	@Override
	@Transactional
	public MovimentoOutputDTO lancarMovimento(MovimentoInputDTO movimento) {
		MovimentoContaPoupancaEntity movimentoContaPoupancaEntity = contaPoupancaRepository.save(MovimentoContaPoupancaEntity.dtoToEntity(movimento));
		
		return MovimentoOutputDTO.entityToDto(movimentoContaPoupancaEntity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MovimentoOutputDTO> consultarMovimento(Conta conta) {
		ContaPoupancaEntity contaPoupancaEntity = null;
		if (conta instanceof ContaPoupancaEntity) {
			contaPoupancaEntity = (ContaPoupancaEntity) conta;
		}
		
		List<MovimentoContaPoupancaEntity> listaMovimentoContaPoupanca = contaPoupancaRepository.findByConta(contaPoupancaEntity, null);
		return MovimentoContaPoupancaEntity.listEntityToList(listaMovimentoContaPoupanca);
	}

	@Override
	public Operacao getInstance() {
		return this;
	}

	@Override
	public TipoConta getTipoOperacao() {
		return TipoConta.CP;
	}

}
