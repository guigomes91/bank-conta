package br.com.gomes.bankconta.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gomes.bankconta.amqp.EnviaEmailComponent;
import br.com.gomes.bankconta.dto.conta.ContaCorrenteInputDTO;
import br.com.gomes.bankconta.dto.conta.ContaCorrenteOutputDTO;
import br.com.gomes.bankconta.entities.cliente.ClienteEntity;
import br.com.gomes.bankconta.entities.conta.ContaCorrenteEntity;
import br.com.gomes.bankconta.repository.ContaCorrenteRepository;
import br.com.gomes.bankconta.service.exceptions.DataIntegrityViolationException;
import br.com.gomes.bankconta.utils.BankGomesConstantes;
import br.com.gomes.bankconta.validators.ClienteValidator;
import br.com.gomes.bankconta.validators.ContaCorrenteValidator;

@Service
public class ContaCorrenteService {

	@Autowired
	private ContaCorrenteRepository ccRepository;
	
	@Autowired
	private EnviaEmailComponent emailComponente;
	
	@Autowired
	private ContaCorrenteValidator ccValidator;
	
	@Autowired
	private ClienteValidator clienteValidator;
	
	@Transactional
	public ContaCorrenteEntity salvar(ContaCorrenteInputDTO ccDTO) {
		clienteValidator.verificaPerfilAdmin(ccDTO.getCliente().getId());
		ContaCorrenteEntity contaCorrenteEntity = new ContaCorrenteEntity().dtoToEntity(ccDTO);
		
		ccValidator.verificaClienteJaTemContaCorrente(contaCorrenteEntity);
		
		Random random = new Random();
		int numeroConta = random.nextInt(BankGomesConstantes.MAX_CONTAS - BankGomesConstantes.MIN_CONTAS) + BankGomesConstantes.MIN_CONTAS;
		int agencia = BankGomesConstantes.NUMERO_AGENCIA;
		
		contaCorrenteEntity.setNumeroConta(numeroConta);
		contaCorrenteEntity.setAgencia(agencia);
		
		ccValidator.verificaContaAgenciaExistente(contaCorrenteEntity);
		
		contaCorrenteEntity = ccRepository.save(contaCorrenteEntity);
		enviarEmailParaCliente(contaCorrenteEntity);
		
		return contaCorrenteEntity;
	}
	
	@Transactional(readOnly = true)
	public BigDecimal getSaldo(int cc) {
		ContaCorrenteEntity contaCorrenteEntity = ccRepository
				.findByNumeroConta(cc)
				.orElseThrow(
						() -> new DataIntegrityViolationException("Conta corrente não encontrada")
						);
		
		return contaCorrenteEntity.getSaldo();
	}
	
	public ContaCorrenteEntity consultarPorId(UUID id) {
		return ccValidator.contaExistente(id);
	}
	
	@Transactional(readOnly = true)
	public Page<ContaCorrenteOutputDTO> extrato(long cc, LocalDate dataInicio, LocalDate dataTermino) {
		return null;
	}

	private void enviarEmailParaCliente(ContaCorrenteEntity contaCorrenteEntity) {
		ClienteEntity clienteEntity = clienteValidator
				.verificaClienteExistente(contaCorrenteEntity.getCliente().getId());
		emailComponente.enviarEmail(clienteEntity.getEmail());
	}
}
