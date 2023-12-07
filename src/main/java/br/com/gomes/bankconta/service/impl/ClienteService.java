package br.com.gomes.bankconta.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.gomes.bankconta.dto.cliente.ClienteDTO;
import br.com.gomes.bankconta.entities.cliente.ClienteEntity;
import br.com.gomes.bankconta.enums.SituacaoCliente;
import br.com.gomes.bankconta.repository.ClienteRepository;
import br.com.gomes.bankconta.validators.ClienteValidator;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private ClienteValidator clienteValidator;

	@Autowired
	private BCryptPasswordEncoder encoder;

	public ClienteEntity create(ClienteDTO clienteDTO) {
		clienteDTO.setId(null);
		clienteDTO.setSenha(encoder.encode(clienteDTO.getSenha()));

		clienteValidator.validaTodasCondicoesParaSalvarCliente(clienteDTO);

		ClienteEntity newObj = new ClienteEntity(clienteDTO);
		
		return repository.save(newObj);
	}

	public ClienteEntity update(ClienteDTO clienteDTO, UUID id) {
		clienteDTO.setId(id);
		ClienteEntity clienteOld = clienteValidator.verificaClienteExistente(id);

		if (!clienteDTO.getSenha().equals(clienteOld.getSenha())) {
			clienteDTO.setSenha(encoder.encode(clienteDTO.getSenha()));
		}

		clienteValidator.validaTodasCondicoesParaSalvarCliente(clienteDTO);

		clienteOld = new ClienteEntity(clienteDTO);
		return repository.save(clienteOld);
	}

	public Page<ClienteEntity> consultar(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "nome");
		
		return new PageImpl<>(repository.findAll(), pageRequest, size);
	}

	public ClienteEntity consultarPorId(UUID id) {
		return clienteValidator.verificaClienteExistente(id);
	}

	public Page<ClienteEntity> consultaPorNomeOuEmail(String searchTerm, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "nome");

		return repository.consultarPorNomeOuEmail(searchTerm.toLowerCase(), pageRequest);
	}

	public void excluir(UUID id) {
		ClienteEntity clienteEntity = clienteValidator.verificaClienteExistente(id);
		clienteEntity.setSituacao(SituacaoCliente.EXCLUIDO);

		repository.save(clienteEntity);
	}

}
