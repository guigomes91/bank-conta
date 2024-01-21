package br.com.gomes.bankconta.service.impl;

import java.util.List;

import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import br.com.gomes.bankconta.dto.movimento.MovimentoOutputDTO;
import br.com.gomes.bankconta.entities.conta.ContaEntity;

public interface Operacao {

	MovimentoOutputDTO lancarMovimento(MovimentoInputDTO movimento);
	List<MovimentoOutputDTO> consultarMovimento(ContaEntity conta); 
}
