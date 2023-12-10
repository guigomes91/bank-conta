package br.com.gomes.bankconta.service.impl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gomes.bankconta.amqp.EnviaEmailComponent;
import br.com.gomes.bankconta.dto.conta.ContaCorrenteInputDTO;
import br.com.gomes.bankconta.entities.cliente.ClienteEntity;
import br.com.gomes.bankconta.entities.conta.ContaCorrenteEntity;
import br.com.gomes.bankconta.repository.ContaCorrenteRepository;
import br.com.gomes.bankconta.validators.ClienteValidator;
import br.com.gomes.bankconta.validators.ContaCorrenteValidator;

@Service
public class ContaCorrenteService {

	private static final int NUMERO_AGENCIA = 2023;
	private static final int MIN_CONTAS = 1;
	private static final int MAX_CONTAS = 200000000;

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
		int numeroConta = random.nextInt(MAX_CONTAS - MIN_CONTAS) + MIN_CONTAS;
		int agencia = NUMERO_AGENCIA;
		
		contaCorrenteEntity.setNumeroConta(numeroConta);
		contaCorrenteEntity.setAgencia(agencia);
		
		ccValidator.verificaContaAgenciaExistente(contaCorrenteEntity);
		
		contaCorrenteEntity = ccRepository.save(contaCorrenteEntity);
		enviarEmailParaCliente(contaCorrenteEntity);
		
		return contaCorrenteEntity;
	}

	private void enviarEmailParaCliente(ContaCorrenteEntity contaCorrenteEntity) {
		ClienteEntity clienteEntity = clienteValidator
				.verificaClienteExistente(contaCorrenteEntity.getCliente().getId());
		emailComponente.enviarEmail(clienteEntity.getEmail());
	}
}
