package br.com.gomes.bankconta.components;

import java.util.List;

import br.com.gomes.bankconta.amqp.transacao.EnviaTransacaoContaCorrenteComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import br.com.gomes.bankconta.dto.movimento.MovimentoOutputDTO;
import br.com.gomes.bankconta.entities.conta.Conta;
import br.com.gomes.bankconta.entities.conta.ContaCorrenteEntity;
import br.com.gomes.bankconta.entities.movimento.MovimentoContaCorrenteEntity;
import br.com.gomes.bankconta.enums.TipoConta;
import br.com.gomes.bankconta.repository.MovimentoContaCorrenteRepository;
import br.com.gomes.bankconta.service.impl.Operacao;
import br.com.gomes.bankconta.validators.ContaValidator;
import br.com.gomes.bankconta.validators.SaldoContaValidator;

@Component
public class MovimentoContaCorrenteComponent implements Operacao {
	
	@Autowired
	private MovimentoContaCorrenteRepository movRepository;

	@Autowired
	private EnviaTransacaoContaCorrenteComponent enviaTransacaoContaCorrenteComponent;
	
	@Autowired
	private ContaValidator ccValidator;
	
	@Autowired
	private SaldoContaValidator saldoValidator;

	@Override
	@Transactional
	public MovimentoOutputDTO lancarMovimento(MovimentoInputDTO movimento) {
		ContaCorrenteEntity contaCorrenteEntity = ccValidator.contaCorrenteExistente(movimento.getConta().getId());
		MovimentoContaCorrenteEntity entity = MovimentoContaCorrenteEntity.dtoToEntity(movimento);
		
		MovimentoOutputDTO movimentoOutputDTO = MovimentoOutputDTO.entityToDto(movRepository.save(entity));
		
		saldoValidator.movimentarSaldoContaCorrente(contaCorrenteEntity, movimento);

		movimento.setId(movimentoOutputDTO.getId());
		enviaTransacaoContaCorrenteComponent.publicarTransacao(movimento);
		
		return movimentoOutputDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public List<MovimentoOutputDTO> consultarMovimento(Conta conta) {
		return MovimentoContaCorrenteEntity.listEntityToListDTO(movRepository.findAll());
	}

	@Override
	public Operacao getInstance() {
		return this;
	}

	@Override
	public TipoConta getTipoOperacao() {
		return TipoConta.CC;
	}

}
