package br.com.gomes.bankconta.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gomes.bankconta.amqp.notificacao.EnviaEmailComponent;
import br.com.gomes.bankconta.dto.cliente.ClienteDTO;
import br.com.gomes.bankconta.dto.conta.ContaPoupancaInputDTO;
import br.com.gomes.bankconta.dto.conta.SaldoDTO;
import br.com.gomes.bankconta.entities.conta.Conta;
import br.com.gomes.bankconta.entities.conta.ContaPoupancaEntity;
import br.com.gomes.bankconta.entities.movimento.MovimentoContaPoupancaEntity;
import br.com.gomes.bankconta.enums.SituacaoConta;
import br.com.gomes.bankconta.repository.ContaCorrenteRepository;
import br.com.gomes.bankconta.repository.ContaPoupancaRepository;
import br.com.gomes.bankconta.repository.MovimentoContaPoupancaRepository;
import br.com.gomes.bankconta.service.exceptions.DataIntegrityViolationException;
import br.com.gomes.bankconta.service.exceptions.ObjectNotFoundException;
import br.com.gomes.bankconta.utils.BankGomesConstantes;
import br.com.gomes.bankconta.validators.ClienteValidator;
import br.com.gomes.bankconta.validators.ContaValidator;
import br.com.gomes.bankconta.validators.SaldoContaValidator;

@Service
public class ContaPoupancaService {

	@Autowired
	private ContaPoupancaRepository contaPoupancaRepository;

	@Autowired
	private ContaCorrenteRepository contaCorrenteRepository;
	
	@Autowired
	private MovimentoContaPoupancaRepository movimentoContaPoupancaRepository;

	@Autowired
	private EnviaEmailComponent emailComponente;

	@Autowired
	private ContaValidator contaValidator;
	
	@Autowired
	private SaldoContaValidator saldoValidator;

	@Autowired
	private ClienteValidator clienteValidator;

	@Transactional
	public ContaPoupancaEntity salvar(ContaPoupancaInputDTO contaPoupancaDTO) {
		clienteValidator.verificaPerfilAdmin(contaPoupancaDTO.getCliente().getId());
		var contaPoupancaEntity = new ContaPoupancaEntity().dtoToEntity(contaPoupancaDTO);

		contaValidator.verificaClienteJaTemContaCorrente(contaPoupancaEntity);
		contaPoupancaEntity.setVariacao(BankGomesConstantes.VARIACAO_POUPANCA);
		contaValidator.verificaContaAgenciaExistente(contaPoupancaEntity);

		var contaCorrenteEntity = contaCorrenteRepository//
				.findByCliente(contaPoupancaDTO.getCliente())//
				.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado!"));
		
		contaPoupancaEntity.setAgencia(contaCorrenteEntity.getAgencia());
		contaPoupancaEntity.setNumeroConta(contaCorrenteEntity.getNumeroConta());

		contaPoupancaEntity = contaPoupancaRepository.save(contaPoupancaEntity);
		enviarEmailParaCliente(contaPoupancaEntity);

		return contaPoupancaEntity;
	}

	@Transactional(readOnly = true)
	public SaldoDTO getSaldo(Long numeroConta) {
		var contaPoupancaEntity = buscaPorNumeroConta(numeroConta);

		return new SaldoDTO(Long.valueOf(contaPoupancaEntity.getNumeroConta()), contaPoupancaEntity.getSaldo());
	}

	public ContaPoupancaEntity consultarPorId(UUID id) {
		return contaValidator.contaPoupancaExistente(id);
	}

	@Transactional(readOnly = true)
	public Page<MovimentoContaPoupancaEntity> extrato(//
			long numeroConta,//
			int page,//
			int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "dataHoraMovimento");
		
		var contaPoupancaEntity = buscaPorNumeroConta(numeroConta);

		return new PageImpl<>(movimentoContaPoupancaRepository//
				.findByConta(contaPoupancaEntity, pageRequest), pageRequest, size);
	}
	
	@Transactional
	public SituacaoConta desativarConta(UUID id) {
		ContaPoupancaEntity contaPoupancaEntity = contaValidator.contaPoupancaExistente(id);
		saldoValidator.verificaSaldoPositivo(contaPoupancaEntity.getSaldo());
		
		contaPoupancaEntity.setSituacaoConta(SituacaoConta.EXCLUIDO);
		contaPoupancaRepository.save(contaPoupancaEntity);
		
		return SituacaoConta.EXCLUIDO;
	}

	private void enviarEmailParaCliente(Conta contaCorrenteEntity) {
		var clienteEntity = clienteValidator//
				.verificaClienteExistente(contaCorrenteEntity.getCliente().getId());
		emailComponente.enviarEmail(new ClienteDTO(clienteEntity), "Conta poupança criada em Gomes Bank", //
				"Parabéns, você acaba de tomar a sua melhor decisão em poupar seu dinheiro!");
	}
	
	private ContaPoupancaEntity buscaPorNumeroConta(Long numeroConta) {
		return contaPoupancaRepository//
				.findByNumeroConta(numeroConta)//
				.orElseThrow(//
						() -> new DataIntegrityViolationException("Conta poupança não encontrada")//
				);
	}
}
