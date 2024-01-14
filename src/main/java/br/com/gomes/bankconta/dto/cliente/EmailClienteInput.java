package br.com.gomes.bankconta.dto.cliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailClienteInput {

	private String email;
	private String assunto;
	private String mensagem;
}
