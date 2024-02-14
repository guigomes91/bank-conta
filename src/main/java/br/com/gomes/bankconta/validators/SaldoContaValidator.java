package br.com.gomes.bankconta.validators;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import br.com.gomes.bankconta.entities.conta.ContaCorrenteEntity;
import br.com.gomes.bankconta.enums.TipoMovimento;
import br.com.gomes.bankconta.repository.ContaCorrenteRepository;
import br.com.gomes.bankconta.resources.exceptions.BadRequestException;

@Component
public class SaldoContaValidator {
	
	@Autowired
	private ContaCorrenteRepository contaCorrenteRepository;

	public void movimentarSaldoContaCorrente(ContaCorrenteEntity contaCorrenteEntity, MovimentoInputDTO movimento) {
		if (movimento.getTipoMovimento() == TipoMovimento.DEBITO) {
			contaCorrenteEntity.setSaldo(contaCorrenteEntity.getSaldo().subtract(movimento.getValor()));
		}
		
		if (movimento.getTipoMovimento() == TipoMovimento.CREDITO) {
			contaCorrenteEntity.setSaldo(contaCorrenteEntity.getSaldo().add(movimento.getValor()));
		}
		
		contaCorrenteRepository.save(contaCorrenteEntity);
	}
	
	public void verificaSaldoNegativo(BigDecimal saldo) {
		if (saldo.doubleValue() <= 0) {
			throw new BadRequestException("Saldo insuficiente!");
		}
	}
	
	public void verificaSaldoPositivo(BigDecimal saldo) {
		if (saldo.doubleValue() > 0) {
			throw new BadRequestException("Saldo positivo na conta!");
		}
	}
}
