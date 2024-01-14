package br.com.gomes.bankconta.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gomes.bankconta.dto.conta.ContaPoupancaInputDTO;
import br.com.gomes.bankconta.dto.conta.ContaPoupancaOutputDTO;
import br.com.gomes.bankconta.entities.conta.ContaPoupancaEntity;
import br.com.gomes.bankconta.service.impl.ContaPoupancaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/contapoupanca")
public class ContaPoupancaController {

	@Autowired
	private ContaPoupancaService contaPoupancaService;
	
	@PostMapping
	public ResponseEntity<ContaPoupancaOutputDTO> salvar(
			@RequestBody @Valid ContaPoupancaInputDTO contaPoupancaDTO) {
		ContaPoupancaEntity contaPoupancaEntity = contaPoupancaService.salvar(contaPoupancaDTO);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(contaPoupancaEntity.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
}
