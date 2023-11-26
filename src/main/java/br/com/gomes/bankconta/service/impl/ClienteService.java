package br.com.gomes.bankconta.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.gomes.bankconta.dto.ClienteDTO;
import br.com.gomes.bankconta.entities.ClienteEntity;
import br.com.gomes.bankconta.repository.ClienteRepository;
import br.com.gomes.bankconta.service.exceptions.DataIntegrityViolationException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public ClienteEntity create(ClienteDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		
		validaPorCpfEEmail(objDTO);
		validaDataNascimento(objDTO);
		
		ClienteEntity newObj = new ClienteEntity(objDTO);
		return repository.save(newObj);
	}

	private void validaDataNascimento(ClienteDTO objDTO) {
		if (Objects.isNull(objDTO.getDataNascimento())) {
			throw new DataIntegrityViolationException("Data de nascimento é obrigatório!");
		}
	}
	
	private void validaPorCpfEEmail(ClienteDTO objDTO) {
		Optional<ClienteEntity> obj = repository.findByCpf(objDTO.getCpf());
		
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}
		
		obj = repository.findByEmail(objDTO.getEmail());
		
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
		}
	}
}
