package br.com.gomes.bankconta.validators;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.gomes.bankconta.entities.conta.Conta;
import br.com.gomes.bankconta.entities.conta.ContaCorrenteEntity;
import br.com.gomes.bankconta.entities.conta.ContaPoupancaEntity;
import br.com.gomes.bankconta.repository.ContaCorrenteRepository;
import br.com.gomes.bankconta.repository.ContaPoupancaRepository;
import br.com.gomes.bankconta.service.exceptions.DataIntegrityViolationException;
import br.com.gomes.bankconta.service.exceptions.ObjectNotFoundException;

@Component
public class ContaValidator {

	@Autowired
	private ContaCorrenteRepository contaCorrenteRepository;
	
	@Autowired
	private ContaPoupancaRepository contaPoupancaRepository;
	
	@Transactional(readOnly = true)
	public void verificaContaAgenciaExistente(Conta ccEntity) {
		Optional<ContaCorrenteEntity> contaCorrenteEntity = contaCorrenteRepository//
				.findByNumeroContaAndAgencia(ccEntity.getNumeroConta(), ccEntity.getAgencia());
		
		contaCorrenteEntity.ifPresent(cc -> {
			new DataIntegrityViolationException("Conta já existe!");
		});
	}
	
	@Transactional(readOnly = true)
	public void verificaClienteJaTemContaCorrente(Conta contaEntity) {
		if (contaEntity instanceof ContaPoupancaEntity) {
			Optional<ContaPoupancaEntity> contaPoupancaEntity = contaPoupancaRepository.findByCliente(contaEntity.getCliente());
			contaPoupancaEntity.ifPresent(contaPoupanca -> {
				throw new DataIntegrityViolationException("Cliente já possui conta poupança aberta!");
			});
		} else {
			Optional<ContaCorrenteEntity> contaCorrenteEntity = contaCorrenteRepository.findByCliente(contaEntity.getCliente());
			contaCorrenteEntity.ifPresent(cc -> {
				throw new DataIntegrityViolationException("Cliente já possui conta corrente aberta!");
			});
		}
	}
	
	@Transactional(readOnly = true)
	public ContaCorrenteEntity contaCorrenteExistente(UUID id) {
		return contaCorrenteRepository.findById(id).orElseThrow(()//
				-> new ObjectNotFoundException("Conta corrente não cadastrada!"));
	}
	
	@Transactional(readOnly = true)
	public ContaPoupancaEntity contaPoupancaExistente(UUID id) {
		return contaPoupancaRepository.findById(id).orElseThrow(()//
				-> new ObjectNotFoundException("Conta poupança não cadastrada!"));
	}
}
