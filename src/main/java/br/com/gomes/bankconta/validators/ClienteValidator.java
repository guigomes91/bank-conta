package br.com.gomes.bankconta.validators;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.gomes.bankconta.dto.cliente.ClienteDTO;
import br.com.gomes.bankconta.entities.cliente.ClienteEntity;
import br.com.gomes.bankconta.enums.SituacaoCliente;
import br.com.gomes.bankconta.repository.ClienteRepository;
import br.com.gomes.bankconta.service.exceptions.DataIntegrityViolationException;
import br.com.gomes.bankconta.service.exceptions.ObjectNotFoundException;

@Component
public class ClienteValidator {
	
	@Autowired
	private ClienteRepository repository;
	
	public void validaTodasCondicoesParaSalvarCliente(ClienteDTO clienteDTO) {
		validaDataNascimento(clienteDTO);
		validaCpf(clienteDTO);
		validaEmail(clienteDTO);
		validarSituacao(clienteDTO);
	}
	
	public ClienteEntity verificaClienteExistente(UUID id) {
		return repository.findById(id).orElseThrow((() -> new ObjectNotFoundException("Cliente não encontrado!")));
	}

	public void validaDataNascimento(ClienteDTO clienteDTO) {
		if (Objects.isNull(clienteDTO.getDataNascimento())) {
			throw new DataIntegrityViolationException("Data de nascimento é obrigatório!");
		}
	}
	
	public void validaCpf(ClienteDTO clienteDTO) {
		Optional<ClienteEntity> obj = repository.findByCpf(clienteDTO.getCpf());
		
		if (obj.isPresent() && obj.get().getId() != clienteDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}
	}
	
	public void validaEmail(ClienteDTO clienteDTO) {		
		 Optional<ClienteEntity> obj = repository.findByEmail(clienteDTO.getEmail());
		
		if (obj.isPresent() && obj.get().getId() != clienteDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
		}
	}
	
	public void validarSituacao(ClienteDTO clienteDTO) {
		if (Objects.isNull(clienteDTO.getSituacao())) {
			throw new DataIntegrityViolationException("Situação é obrigatória!");
		}
		
		SituacaoCliente situacao = SituacaoCliente.toEnum(clienteDTO.getSituacao().getCodigo());
		if (situacao == null) {
			throw new DataIntegrityViolationException("Situação inválida");
		}
	}
}
