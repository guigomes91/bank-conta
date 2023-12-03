package br.com.gomes.bankconta.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

	public List<ClienteEntity> consultar() {
		return repository.findAll();
	}
	
	public ClienteEntity consultarPorId(UUID id) {
		return clienteValidator.verificaClienteExistente(id);
	}
	
	public void excluir(UUID id) {
		ClienteEntity clienteEntity = clienteValidator.verificaClienteExistente(id);
		clienteEntity.setSituacao(SituacaoCliente.EXCLUIDO);
		
		repository.save(clienteEntity);
	}
	
}
