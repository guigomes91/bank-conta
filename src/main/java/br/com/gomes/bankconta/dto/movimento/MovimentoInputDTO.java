package br.com.gomes.bankconta.dto.movimento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.gomes.bankconta.entities.conta.Conta;
import br.com.gomes.bankconta.enums.TipoMovimento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimentoInputDTO {
	
	private LocalDateTime dataHoraMovimento = LocalDateTime.now();
	
	@NotNull
	@NotBlank
	private String numeroDocumento;
	
	private BigDecimal valor = BigDecimal.ZERO;
	
	@NotNull
	@NotBlank
	private String descricao;
	
	private TipoMovimento tipoMovimento;

	private Conta conta;
}
