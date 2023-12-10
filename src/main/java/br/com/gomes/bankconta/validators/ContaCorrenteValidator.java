package br.com.gomes.bankconta.validators;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.gomes.bankconta.entities.conta.ContaCorrenteEntity;
import br.com.gomes.bankconta.repository.ContaCorrenteRepository;
import br.com.gomes.bankconta.service.exceptions.DataIntegrityViolationException;

@Component
public class ContaCorrenteValidator {

	@Autowired
	private ContaCorrenteRepository ccRepository;
	
	public void verificaContaAgenciaExistente(ContaCorrenteEntity ccEntity) {
		Optional<ContaCorrenteEntity> contaCorrenteEntity = ccRepository.findByNumeroContaAndAgencia(ccEntity.getNumeroConta(), ccEntity.getAgencia());
		
		contaCorrenteEntity.ifPresent(cc -> {
			new DataIntegrityViolationException("Conta já existe!");
		});
	}
	
	public void verificaClienteJaTemContaCorrente(ContaCorrenteEntity ccEntity) {
		Optional<ContaCorrenteEntity> contaCorrenteEntity = ccRepository.findByCliente(ccEntity.getCliente());
		
		contaCorrenteEntity.ifPresent(cc -> {
			new DataIntegrityViolationException("Cliente já possui conta corrente aberta!");
		});
	}
}
