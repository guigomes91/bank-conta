package br.com.gomes.bankconta.components;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import br.com.gomes.bankconta.dto.movimento.MovimentoOutputDTO;
import br.com.gomes.bankconta.entities.conta.Conta;
import br.com.gomes.bankconta.service.impl.Operacao;

@Component
public class MovimentoContaPoupancaComponent implements Operacao {

	@Override
	public MovimentoOutputDTO lancarMovimento(MovimentoInputDTO movimento) {
		return null;
	}

	@Override
	public List<MovimentoOutputDTO> consultarMovimento(Conta conta) {
		return null;
	}

}
