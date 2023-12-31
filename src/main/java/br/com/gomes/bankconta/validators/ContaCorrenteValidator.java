package br.com.gomes.bankconta.validators;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.gomes.bankconta.entities.conta.ContaCorrenteEntity;
import br.com.gomes.bankconta.repository.ContaCorrenteRepository;
import br.com.gomes.bankconta.service.exceptions.DataIntegrityViolationException;
import br.com.gomes.bankconta.service.exceptions.ObjectNotFoundException;

@Component
public class ContaCorrenteValidator {

	@Autowired
	private ContaCorrenteRepository ccRepository;
	
	@Transactional(readOnly = true)
	public void verificaContaAgenciaExistente(ContaCorrenteEntity ccEntity) {
		Optional<ContaCorrenteEntity> contaCorrenteEntity = ccRepository.findByNumeroContaAndAgencia(ccEntity.getNumeroConta(), ccEntity.getAgencia());
		
		contaCorrenteEntity.ifPresent(cc -> {
			new DataIntegrityViolationException("Conta já existe!");
		});
	}
	
	@Transactional(readOnly = true)
	public void verificaClienteJaTemContaCorrente(ContaCorrenteEntity ccEntity) {
		Optional<ContaCorrenteEntity> contaCorrenteEntity = ccRepository.findByCliente(ccEntity.getCliente());
		
		contaCorrenteEntity.ifPresent(cc -> {
			throw new DataIntegrityViolationException("Cliente já possui conta corrente aberta!");
		});
	}
	
	@Transactional(readOnly = true)
	public ContaCorrenteEntity contaExistente(UUID id) {
		return ccRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Conta não cadastrada!"));
	}
}
