package br.com.gomes.bankconta.dto.movimento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.gomes.bankconta.entities.conta.ContaCorrenteEntity;
import br.com.gomes.bankconta.enums.TipoMovimento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimentoInputDTO {
	
	private UUID id;

	@JsonIgnore
	private LocalDateTime dataHoraMovimento = LocalDateTime.now();
	
	@NotNull
	@NotBlank
	private String numeroDocumento;
	
	private BigDecimal valor = BigDecimal.ZERO;
	
	@NotNull
	@NotBlank
	private String descricao;
	
	private TipoMovimento tipoMovimento;

	private ContaCorrenteEntity conta;

	public MovimentoInputDTO(TipoMovimento tipoMovimento, BigDecimal valor) {
		this.tipoMovimento = tipoMovimento;
		this.valor = valor;
	}
}
