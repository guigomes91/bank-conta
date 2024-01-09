package br.com.gomes.bankconta.controller;

import java.net.URI;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gomes.bankconta.dto.conta.ContaCorrenteOutputDTO;
import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import br.com.gomes.bankconta.dto.movimento.MovimentoOutputDTO;
import br.com.gomes.bankconta.enums.TipoConta;
import br.com.gomes.bankconta.service.impl.MovimentoServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/movimentocc/")
public class MovimentoContaCorrenteController {
	
	@Autowired
	private MovimentoServiceImpl movimentoService;

	@PostMapping(value = "lancar")
	public ResponseEntity<MovimentoOutputDTO> lancarMovimento(
			@Valid @RequestBody MovimentoInputDTO movDTO) {
		MovimentoOutputDTO movimentoOutput = movimentoService.lancarMovimento(TipoConta.CC, movDTO);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(movimentoOutput.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping(value = "/{cc}")
	public ResponseEntity<ContaCorrenteOutputDTO> consultaPorDocumento(
			@PathVariable long cc,
			@RequestParam(value = "numeroDocumento", required = true) LocalDate dataInicio) {
		
		return ResponseEntity.ok().build();
	}
	
}
