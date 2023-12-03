package br.com.gomes.bankconta.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gomes.bankconta.dto.ClienteDTO;
import br.com.gomes.bankconta.entities.ClienteEntity;
import br.com.gomes.bankconta.service.impl.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

	@Autowired
	private ClienteService service;

	@PostMapping
	public ResponseEntity<ClienteDTO> salvar(@Valid @RequestBody ClienteDTO objDTO) {
		ClienteEntity newObj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> atualizar(@Valid @RequestBody ClienteDTO objDTO, @PathVariable UUID id) {
		ClienteEntity clienteEntity = service.update(objDTO, id);
		return ResponseEntity.ok().body(new ClienteDTO(clienteEntity));
	}

	@GetMapping
	public ResponseEntity<List<ClienteDTO>> consultar() {
		List<ClienteEntity> clientesEntity = service.consultar();

		return ResponseEntity.ok(new ClienteDTO().entityToListDTO(clientesEntity));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> excluir(@PathVariable UUID id) {
		service.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
