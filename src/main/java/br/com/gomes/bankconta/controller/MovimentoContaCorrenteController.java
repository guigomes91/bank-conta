package br.com.gomes.bankconta.controller;

import br.com.gomes.bankconta.components.MovimentoContaCorrenteComponent;
import br.com.gomes.bankconta.dto.conta.ContaCorrenteOutputDTO;
import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import br.com.gomes.bankconta.dto.movimento.MovimentoOutputDTO;
import br.com.gomes.bankconta.dto.movimento.TransferenciaInputDTO;
import br.com.gomes.bankconta.dto.movimento.TransferenciaOutputDTO;
import br.com.gomes.bankconta.enums.TipoConta;
import br.com.gomes.bankconta.service.impl.MovimentoServiceImpl;
import br.com.gomes.bankconta.utils.BankGomesConstantes;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(value = "/movimentocc")
public class MovimentoContaCorrenteController {
	@Autowired
	private MovimentoServiceImpl movimentoService;

	@Autowired
	private MovimentoContaCorrenteComponent movimentoContaCorrenteComponent;

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
			@RequestParam(value = "numeroDocumento", required = true) String numeroDocumento) {
		
		return ResponseEntity.ok().build();
	}

	@PostMapping(value = "/transferir")
	public ResponseEntity<TransferenciaOutputDTO> transferir(@RequestBody @Valid TransferenciaInputDTO transferenciaInputDTO) {
		UUID identificador = movimentoContaCorrenteComponent.transferirContaCorrente(transferenciaInputDTO);
		return ResponseEntity.ok(new TransferenciaOutputDTO(identificador, BankGomesConstantes.TRANSFERENCIA_CONCLUIDA));
	}
}
