package br.com.gomes.bankconta.controller;

import java.net.URI;
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

import br.com.gomes.bankconta.dto.conta.ContaPoupancaInputDTO;
import br.com.gomes.bankconta.dto.conta.ContaPoupancaOutputDTO;
import br.com.gomes.bankconta.dto.conta.SaldoDTO;
import br.com.gomes.bankconta.dto.movimento.MovimentoOutputDTO;
import br.com.gomes.bankconta.entities.conta.ContaPoupancaEntity;
import br.com.gomes.bankconta.entities.movimento.MovimentoContaPoupancaEntity;
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
	
	@GetMapping(value = "/saldo/{numeroConta}")
	public ResponseEntity<SaldoDTO> visualizarSaldo(@PathVariable Long numeroConta) {
		SaldoDTO saldoDTO = contaPoupancaService.getSaldo(numeroConta);
		
		return ResponseEntity.ok(saldoDTO);
	}
	
	@GetMapping(value = "/extrato/{numeroConta}")
	public ResponseEntity<Page<MovimentoOutputDTO>> extrato(
			@PathVariable Long numeroConta, 
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {
		
		Page<MovimentoContaPoupancaEntity> movimentosEntity = contaPoupancaService.extrato(
				numeroConta, 
				page, 
				size);
		
		return ResponseEntity.ok(movimentosEntity.map(MovimentoOutputDTO::entityToDto));
	}
	
	public <T> ResponseEntity<T> desativarConta() {
		return null;
	}
}
