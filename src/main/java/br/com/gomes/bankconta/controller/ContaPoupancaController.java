package br.com.gomes.bankconta.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gomes.bankconta.dto.conta.ContaPoupancaInputDTO;
import br.com.gomes.bankconta.dto.conta.ContaPoupancaOutputDTO;
import br.com.gomes.bankconta.dto.conta.SaldoDTO;
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
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ContaPoupancaOutputDTO> consultarPorId(@PathVariable UUID id) {
		ContaPoupancaEntity contaPoupancaEntity = contaPoupancaService.consultarPorId(id);

		return ResponseEntity.ok(new ContaPoupancaOutputDTO(contaPoupancaEntity));
	}
	
	@GetMapping(value = "/saldo/{cc}")
	public ResponseEntity<SaldoDTO> visualizarSaldo(@PathVariable int cc) {
		SaldoDTO saldoDTO = contaPoupancaService.getSaldo(cc);
		
		return ResponseEntity.ok(saldoDTO);
	}
}
