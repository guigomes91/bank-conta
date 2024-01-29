package br.com.gomes.bankconta.validators;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import br.com.gomes.bankconta.entities.conta.ContaCorrenteEntity;
import br.com.gomes.bankconta.entities.conta.ContaPoupancaEntity;
import br.com.gomes.bankconta.enums.TipoMovimento;
import br.com.gomes.bankconta.repository.ContaCorrenteRepository;

@Component
public class SaldoContaValidator {
	
	@Autowired
	private ContaCorrenteRepository contaCorrenteRepository;
	
	@Autowired
	private ContaValidator contaValidator;

	public void movimentarSaldo(ContaCorrenteEntity contaCorrenteEntity, MovimentoInputDTO movimento) {
		if (movimento.getTipoMovimento() == TipoMovimento.DEBITO) {
			contaCorrenteEntity.setSaldo(contaCorrenteEntity.getSaldo().subtract(movimento.getValor()));
		}
		
		if (movimento.getTipoMovimento() == TipoMovimento.CREDITO) {
			contaCorrenteEntity.setSaldo(contaCorrenteEntity.getSaldo().add(movimento.getValor()));
		}
		
		contaCorrenteRepository.save(contaCorrenteEntity);
	}
	
	public BigDecimal consultaSaldoPoupanca(UUID id) {
		ContaPoupancaEntity contaPoupancaEntity = contaValidator.contaPoupancaExistente(id);
		return contaPoupancaEntity.getSaldo();
	}
}
