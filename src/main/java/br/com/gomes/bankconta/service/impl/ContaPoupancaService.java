package br.com.gomes.bankconta.service.impl;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gomes.bankconta.amqp.EnviaEmailComponent;
import br.com.gomes.bankconta.dto.cliente.ClienteDTO;
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
import br.com.gomes.bankconta.service.exceptions.ObjectNotFoundException;
import br.com.gomes.bankconta.utils.BankGomesConstantes;
import br.com.gomes.bankconta.validators.ClienteValidator;
import br.com.gomes.bankconta.validators.ContaValidator;

@Service
public class ContaPoupancaService {

	@Autowired
	private ContaPoupancaRepository contaPoupancaRepository;
	
	@Autowired
	private ContaCorrenteRepository contaCorrenteRepository;
	
	@Autowired
	private EnviaEmailComponent emailComponente;
	
	@Autowired
	private ContaValidator contaValidator;
	
	@Autowired
	private ClienteValidator clienteValidator;
	
	@Transactional
	public ContaPoupancaEntity salvar(ContaPoupancaInputDTO contaPoupancaDTO) {
		clienteValidator.verificaPerfilAdmin(contaPoupancaDTO.getCliente().getId());
		ContaPoupancaEntity contaPoupancaEntity = new ContaPoupancaEntity().dtoToEntity(contaPoupancaDTO);
		
		contaValidator.verificaClienteJaTemContaCorrente(contaPoupancaEntity);
		contaPoupancaEntity.setVariacao(BankGomesConstantes.VARIACAO_POUPANCA);
		contaValidator.verificaContaAgenciaExistente(contaPoupancaEntity);
		
		ContaCorrenteEntity contaCorrenteEntity = contaCorrenteRepository.findByCliente(contaPoupancaDTO.getCliente()).orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado!"));
		contaPoupancaEntity.setAgencia(contaCorrenteEntity.getAgencia());
		contaPoupancaEntity.setNumeroConta(contaCorrenteEntity.getNumeroConta());
		
		contaPoupancaEntity = contaPoupancaRepository.save(contaPoupancaEntity);
		enviarEmailParaCliente(contaPoupancaEntity);
		
		return contaPoupancaEntity;
	}
	
	@Transactional(readOnly = true)
	public SaldoDTO getSaldo(int cc) {
		ContaPoupancaEntity contaPoupancaEntity = contaPoupancaRepository
				.findByNumeroConta(cc)
				.orElseThrow(
						() -> new DataIntegrityViolationException("Conta poupança não encontrada")
						);
		
		return new SaldoDTO(Long.valueOf(contaPoupancaEntity.getNumeroConta()), contaPoupancaEntity.getSaldo());
	}
	
	public ContaPoupancaEntity consultarPorId(UUID id) {
		return contaValidator.contaPoupancaExistente(id);
	}
	
	@Transactional(readOnly = true)
	public Page<ContaPoupancaOutputDTO> extrato(long cc, LocalDate dataInicio, LocalDate dataTermino) {
		return null;
	}

	private void enviarEmailParaCliente(Conta contaCorrenteEntity) {
		ClienteEntity clienteEntity = clienteValidator//
				.verificaClienteExistente(contaCorrenteEntity.getCliente().getId());
		emailComponente.enviarEmail(new ClienteDTO(clienteEntity), "Conta poupança criada em Gomes Bank",//
				"Parabéns, você acaba de tomar a sua melhor decisão em poupar seu dinheiro!");
	}
}
