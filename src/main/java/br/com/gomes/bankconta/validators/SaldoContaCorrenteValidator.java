package br.com.gomes.bankconta.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import br.com.gomes.bankconta.entities.conta.ContaCorrenteEntity;
import br.com.gomes.bankconta.enums.TipoMovimento;
import br.com.gomes.bankconta.repository.ContaCorrenteRepository;

@Component
public class SaldoContaCorrenteValidator {
	
	@Autowired
	private ContaCorrenteRepository ccRepository;

	public void tratarSaldoContaCorrente(ContaCorrenteEntity contaCorrenteEntity, MovimentoInputDTO movimento) {
		if (movimento.getTipoMovimento() == TipoMovimento.DEBITO) {
			contaCorrenteEntity.setSaldo(contaCorrenteEntity.getSaldo().subtract(movimento.getValor()));
		}
		
		if (movimento.getTipoMovimento() == TipoMovimento.CREDITO) {
			contaCorrenteEntity.setSaldo(contaCorrenteEntity.getSaldo().add(movimento.getValor()));
		}
		
		ccRepository.save(contaCorrenteEntity);
	}
}
