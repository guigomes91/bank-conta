package br.com.gomes.bankconta.service.impl;

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
import br.com.gomes.bankconta.dto.conta.ContaPoupancaInputDTO;
import br.com.gomes.bankconta.dto.conta.ContaPoupancaOutputDTO;
import br.com.gomes.bankconta.dto.conta.SaldoDTO;
import br.com.gomes.bankconta.entities.cliente.ClienteEntity;
import br.com.gomes.bankconta.entities.conta.Conta;
import br.com.gomes.bankconta.entities.conta.ContaCorrenteEntity;
import br.com.gomes.bankconta.entities.conta.ContaPoupancaEntity;
import br.com.gomes.bankconta.repository.ContaCorrenteRepository;
import br.com.gomes.bankconta.repository.ContaPoupancaRepository;
import br.com.gomes.bankconta.service.exceptions.DataIntegrityViolationException;
import br.com.gomes.bankconta.utils.BankGomesConstantes;
import br.com.gomes.bankconta.validators.ClienteValidator;
import br.com.gomes.bankconta.validators.ContaCorrenteValidator;

@Service
public class ContaPoupancaService {

	@Autowired
	private ContaPoupancaRepository cpRepository;
	
	@Autowired
	private EnviaEmailComponent emailComponente;
	
	@Autowired
	private ContaCorrenteValidator ccValidator;
	
	@Autowired
	private ClienteValidator clienteValidator;
	
	@Transactional
	public ContaPoupancaEntity salvar(ContaPoupancaInputDTO ccDTO) {
		clienteValidator.verificaPerfilAdmin(ccDTO.getCliente().getId());
		ContaPoupancaEntity contaPoupancaEntity = new ContaPoupancaEntity().dtoToEntity(ccDTO);
		
		ccValidator.verificaClienteJaTemContaCorrente(contaPoupancaEntity);
		
		contaPoupancaEntity.setVariacao(BankGomesConstantes.VARIACAO_POUPANCA);
		
		ccValidator.verificaContaAgenciaExistente(contaPoupancaEntity);
		
		contaPoupancaEntity = cpRepository.save(contaPoupancaEntity);
		enviarEmailParaCliente(contaPoupancaEntity);
		
		return contaPoupancaEntity;
	}
	
	@Transactional(readOnly = true)
	public SaldoDTO getSaldo(int cc) {
		ContaPoupancaEntity contaCorrenteEntity = cpRepository
				.findByNumeroConta(cc)
				.orElseThrow(
						() -> new DataIntegrityViolationException("Conta poupança não encontrada")
						);
		
		return new SaldoDTO(Long.valueOf(contaCorrenteEntity.getNumeroConta()), contaCorrenteEntity.getSaldo());
	}
	
	public ContaPoupancaEntity consultarPorId(UUID id) {
		//return ccValidator.contaExistente(id);
		return null;
	}
	
	@Transactional(readOnly = true)
	public Page<ContaPoupancaOutputDTO> extrato(long cc, LocalDate dataInicio, LocalDate dataTermino) {
		return null;
	}

	private void enviarEmailParaCliente(Conta contaCorrenteEntity) {
		ClienteEntity clienteEntity = clienteValidator
				.verificaClienteExistente(contaCorrenteEntity.getCliente().getId());
		emailComponente.enviarEmail(clienteEntity.getEmail());
	}
}
