package br.com.gomes.bankconta.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.gomes.bankconta.dto.ClienteDTO;
import br.com.gomes.bankconta.entities.ClienteEntity;
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

	public ClienteEntity create(ClienteDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));

		clienteValidator.validaTodasCondicoesParaSalvarCliente(objDTO);

		ClienteEntity newObj = new ClienteEntity(objDTO);
		return repository.save(newObj);
	}

	public ClienteEntity update(ClienteDTO objDTO, UUID id) {
		objDTO.setId(id);
		ClienteEntity clienteOld = clienteValidator.verificaClienteExistente(id);

		if (!objDTO.getSenha().equals(clienteOld.getSenha())) {
			objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		}

		clienteValidator.validaTodasCondicoesParaSalvarCliente(objDTO);

		clienteOld = new ClienteEntity(objDTO);
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
