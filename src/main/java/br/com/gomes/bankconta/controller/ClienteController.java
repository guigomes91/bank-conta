package br.com.gomes.bankconta.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gomes.bankconta.amqp.EnviaEmailComponent;
import br.com.gomes.bankconta.dto.cliente.ClienteDTO;
import br.com.gomes.bankconta.entities.cliente.ClienteEntity;
import br.com.gomes.bankconta.service.impl.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

	@Autowired
	private ClienteService service;
	
	@Autowired
	private EnviaEmailComponent emailComponent;

	@PostMapping
	public ResponseEntity<ClienteDTO> salvar(@Valid @RequestBody ClienteDTO clienteDTO) {
		ClienteEntity clienteEntity = service.create(clienteDTO);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(clienteEntity.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> atualizar(
			@Valid @RequestBody ClienteDTO clienteDTO, 
			@PathVariable UUID id) {
		ClienteEntity clienteEntity = service.update(clienteDTO, id);
		return ResponseEntity
				.ok()
				.body(new ClienteDTO(clienteEntity));
	}

	@GetMapping
	public ResponseEntity<Page<ClienteDTO>> consultar(
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {

		Page<ClienteEntity> clientesEntityPage = service.consultar(page, size);

		return ResponseEntity.ok(new ClienteDTO().entityPageToDTO(clientesEntityPage));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> consultarPorId(@PathVariable UUID id) {
		ClienteEntity clienteEntity = service.consultarPorId(id);

		return ResponseEntity.ok(new ClienteDTO(clienteEntity));
	}

	@GetMapping("/consultarPorTermo")
	public ResponseEntity<Page<ClienteDTO>> consultarPorNomeOuEmail(
			@RequestParam("searchTerm") String searchTerm,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {
		
		Page<ClienteEntity> clienteEntityPage = service.consultaPorNomeOuEmail(searchTerm, page, size);
		
		return ResponseEntity.ok(new ClienteDTO().entityPageToDTO(clienteEntityPage));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> excluir(@PathVariable UUID id) {
		service.excluir(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/enviaEmail")
	public ResponseEntity<String> enviaEmail(@RequestBody String email) {
		try {
			emailComponent.enviarEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok("Email enviado");
	}
}
