package br.com.gomes.bankconta.dto.movimento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.gomes.bankconta.entities.conta.Conta;
import br.com.gomes.bankconta.enums.TipoMovimento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimentoInputDTO {

	private UUID id;
	
	private LocalDateTime dataHoraMovimento = LocalDateTime.now();
	
	private String numeroDocumento;
	
	private BigDecimal valor = BigDecimal.ZERO;
	
	private String descricao;
	
	private TipoMovimento tipoMovimento;

	private Conta conta;
}
