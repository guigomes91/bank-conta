package br.com.gomes.bankconta.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gomes.bankconta.dto.conta.ContaCorrenteInputDTO;
import br.com.gomes.bankconta.dto.conta.ContaCorrenteOutputDTO;
import br.com.gomes.bankconta.dto.conta.SaldoDTO;
import br.com.gomes.bankconta.entities.conta.ContaCorrenteEntity;
import br.com.gomes.bankconta.service.impl.ContaCorrenteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/contacorrente")
public class ContaCorrenteController {
	
	@Autowired
	private ContaCorrenteService ccService;

	@PostMapping
	public ResponseEntity<ContaCorrenteOutputDTO> salvar(
			@RequestBody @Valid ContaCorrenteInputDTO ccDTO) {
		ContaCorrenteEntity ccEntity = ccService.salvar(ccDTO);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(ccEntity.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ContaCorrenteOutputDTO> consultarPorId(@PathVariable UUID id) {
		ContaCorrenteEntity ccEntity = ccService.consultarPorId(id);

		return ResponseEntity.ok(new ContaCorrenteOutputDTO(ccEntity));
	}
	
	@GetMapping(value = "/extrato/{cc}")
	public ResponseEntity<Page<ContaCorrenteOutputDTO>> extrato(
			@PathVariable long cc, 
			@RequestParam(value = "dataInicio", required = true) LocalDate dataInicio,
			@RequestParam(value = "dataTermino", required = true) LocalDate dataTermino) {
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(value = "/saldo/{cc}")
	public ResponseEntity<SaldoDTO> visualizarSaldo(@PathVariable int cc) {
		SaldoDTO saldoDTO = ccService.getSaldo(cc);
		
		return ResponseEntity.ok(saldoDTO);
	}
}
