package br.com.gomes.bankconta.dto.movimento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.gomes.bankconta.entities.movimento.MovimentoContaCorrenteEntity;
import br.com.gomes.bankconta.enums.TipoMovimento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimentoOutputDTO {

	private UUID id;
	
	private LocalDateTime dataHoraMovimento = LocalDateTime.now();
	
	private String numeroDocumento;
	
	private BigDecimal valor = BigDecimal.ZERO;
	
	private String descricao;
	
	private TipoMovimento tipoMovimento;
	
	public static MovimentoOutputDTO entityToDto(MovimentoContaCorrenteEntity entity) {
		MovimentoOutputDTO dto = new MovimentoOutputDTO();
		dto.setId(entity.getId());
		dto.setDataHoraMovimento(entity.getDataHoraMovimento());
		dto.setNumeroDocumento(entity.getNumeroDocumento());
		dto.setValor(entity.getValor());
		dto.setDescricao(entity.getDescricao());
		dto.setTipoMovimento(entity.getTipoMovimento());
		
		return dto;
	}
}
