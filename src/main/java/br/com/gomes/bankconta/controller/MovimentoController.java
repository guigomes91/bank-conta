package br.com.gomes.bankconta.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import br.com.gomes.bankconta.dto.movimento.MovimentoOutputDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/movimento")
public class MovimentoController {

	@PostMapping(value = "lancar/{cc}")
	public ResponseEntity<MovimentoOutputDTO> lancarMovimento(@Valid @RequestBody MovimentoInputDTO mov,
			@PathVariable long cc) {
		return ResponseEntity.ok().build();
	}
}
