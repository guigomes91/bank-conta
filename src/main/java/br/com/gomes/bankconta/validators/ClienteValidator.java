package br.com.gomes.bankconta.validators;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.gomes.bankconta.dto.ClienteDTO;
import br.com.gomes.bankconta.entities.ClienteEntity;
import br.com.gomes.bankconta.repository.ClienteRepository;
import br.com.gomes.bankconta.service.exceptions.DataIntegrityViolationException;

@Component
public class ClienteValidator {
	
	@Autowired
	private ClienteRepository repository;
	
	public void validaTodasCondicoesParaSalvarCliente(ClienteDTO objDTO) {
		validaDataNascimento(objDTO);
		validaCpf(objDTO);
		validaEmail(objDTO);
	}

	public void validaDataNascimento(ClienteDTO objDTO) {
		if (Objects.isNull(objDTO.getDataNascimento())) {
			throw new DataIntegrityViolationException("Data de nascimento é obrigatório!");
		}
	}
	
	public void validaCpf(ClienteDTO objDTO) {
		Optional<ClienteEntity> obj = repository.findByCpf(objDTO.getCpf());
		
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}
	}
	
	public void validaEmail(ClienteDTO objDTO) {		
		 Optional<ClienteEntity> obj = repository.findByEmail(objDTO.getEmail());
		
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
		}
	}
}
