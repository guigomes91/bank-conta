package br.com.gomes.bankconta.service.impl;

import br.com.gomes.bankconta.components.EnviadorEmailComponent;
import br.com.gomes.bankconta.dto.conta.ContaCorrenteInputDTO;
import br.com.gomes.bankconta.dto.conta.ContaCorrenteOutputDTO;
import br.com.gomes.bankconta.dto.conta.SaldoDTO;
import br.com.gomes.bankconta.entities.conta.ContaCorrenteEntity;
import br.com.gomes.bankconta.enums.SituacaoConta;
import br.com.gomes.bankconta.repository.ContaCorrenteRepository;
import br.com.gomes.bankconta.service.exceptions.DataIntegrityViolationException;
import br.com.gomes.bankconta.utils.BankGomesConstantes;
import br.com.gomes.bankconta.validators.ClienteValidator;
import br.com.gomes.bankconta.validators.ContaValidator;
import br.com.gomes.bankconta.validators.SaldoContaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@Service
public class ContaCorrenteService {

	@Autowired
	private ContaCorrenteRepository contaCorrenteRepository;
	
	@Autowired
	private EnviadorEmailComponent emailComponente;
	
	@Autowired
	private ContaValidator contaCorrenteValidator;
	
	@Autowired
	private ClienteValidator clienteValidator;
	
	@Autowired
	private SaldoContaValidator saldoValidator;
	
	@Transactional
	public ContaCorrenteEntity salvar(ContaCorrenteInputDTO ccDTO) {
		clienteValidator.verificaPerfilAdmin(ccDTO.getCliente().getId());
		ContaCorrenteEntity contaCorrenteEntity = new ContaCorrenteEntity().dtoToEntity(ccDTO);
		
		contaCorrenteValidator.verificaClienteJaTemContaCorrente(contaCorrenteEntity);
		
		Random random = new Random();
		int numeroConta = random.nextInt(BankGomesConstantes.MAX_CONTAS - BankGomesConstantes.MIN_CONTAS) + BankGomesConstantes.MIN_CONTAS;
		int agencia = BankGomesConstantes.NUMERO_AGENCIA;
		
		contaCorrenteEntity.setNumeroConta(numeroConta);
		contaCorrenteEntity.setAgencia(agencia);
		
		contaCorrenteValidator.verificaContaAgenciaExistente(contaCorrenteEntity);
		
		contaCorrenteEntity = contaCorrenteRepository.save(contaCorrenteEntity);
		emailComponente.enviarEmailParaCliente(contaCorrenteEntity,
				BankGomesConstantes.ASSUNTO_CRIACAO_CONTA,
				BankGomesConstantes.MENSAGEM_CRIACAO_CONTA
		);
		
		return contaCorrenteEntity;
	}
	
	@Transactional(readOnly = true)
	public SaldoDTO getSaldo(int cc) {
		ContaCorrenteEntity contaCorrenteEntity = contaCorrenteRepository
				.findByNumeroConta(cc)
				.orElseThrow(
						() -> new DataIntegrityViolationException("Conta corrente não encontrada")
						);
		
		return new SaldoDTO(Long.valueOf(contaCorrenteEntity.getNumeroConta()), contaCorrenteEntity.getSaldo());
	}
	
	public ContaCorrenteEntity consultarPorId(UUID id) {
		return contaCorrenteValidator.contaCorrenteExistente(id);
	}
	
	@Transactional(readOnly = true)
	public Page<ContaCorrenteOutputDTO> extrato(long cc, LocalDate dataInicio, LocalDate dataTermino) {
		return null;
	}

	@Transactional
	public SituacaoConta desativarConta(UUID id) {
		ContaCorrenteEntity contaCorrenteEntity = contaCorrenteValidator.contaCorrenteExistente(id);
		saldoValidator.verificaSaldoPositivo(contaCorrenteEntity.getSaldo());
		
		contaCorrenteEntity.setSituacaoConta(SituacaoConta.EXCLUIDO);
		contaCorrenteRepository.save(contaCorrenteEntity);
		
		return contaCorrenteEntity.getSituacaoConta();
	}
}
