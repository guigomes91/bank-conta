package br.com.gomes.bankconta.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gomes.bankconta.dto.conta.ContaCorrenteInputDTO;
import br.com.gomes.bankconta.dto.conta.ContaCorrenteOutputDTO;
import br.com.gomes.bankconta.entities.conta.ContaCorrenteEntity;
import br.com.gomes.bankconta.service.impl.ContaCorrenteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/contacorrente")
public class ContaCorrenteController {
	
	@Autowired
	private ContaCorrenteService ccService;

	@PostMapping
	public ResponseEntity<ContaCorrenteOutputDTO> salvar(@RequestBody @Valid ContaCorrenteInputDTO ccDTO) {
		ContaCorrenteEntity ccEntity = ccService.salvar(ccDTO);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(ccEntity.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
}
