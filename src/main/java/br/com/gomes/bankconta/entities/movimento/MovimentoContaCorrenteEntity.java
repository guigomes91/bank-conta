package br.com.gomes.bankconta.entities.movimento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import br.com.gomes.bankconta.dto.movimento.MovimentoOutputDTO;
import br.com.gomes.bankconta.entities.conta.Conta;
import br.com.gomes.bankconta.enums.TipoMovimento;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MovimentoContaCorrenteEntity extends MovimentoEntity {

	public MovimentoContaCorrenteEntity(
			UUID id,
			LocalDateTime dataHoraMovimento,
			String numeroDocumento,
			BigDecimal valor,
			String descricao,
			TipoMovimento tipoMovimento,
			Conta conta) {
		super(id, dataHoraMovimento, numeroDocumento, valor, descricao, tipoMovimento, conta);
	}

	public static MovimentoContaCorrenteEntity dtoToEntity(MovimentoInputDTO input) {
		MovimentoContaCorrenteEntity entity = new MovimentoContaCorrenteEntity();
		entity.setConta(input.getConta());
		entity.setDataHoraMovimento(input.getDataHoraMovimento());
		entity.setDescricao(input.getDescricao());
		entity.setId(input.getId());
		entity.setNumeroDocumento(input.getNumeroDocumento());
		entity.setTipoMovimento(input.getTipoMovimento());
		entity.setValor(input.getValor());
		
		return entity;
	}
	
	public static MovimentoOutputDTO entityToDTO(MovimentoContaCorrenteEntity input) {
		MovimentoOutputDTO dto = new MovimentoOutputDTO();
		
		dto.setDataHoraMovimento(input.getDataHoraMovimento());
		dto.setDescricao(input.getDescricao());
		dto.setId(input.getId());
		dto.setNumeroDocumento(input.getNumeroDocumento());
		dto.setTipoMovimento(input.getTipoMovimento());
		dto.setValor(input.getValor());
		
		return dto;
	}
	
	public static List<MovimentoOutputDTO> listEntityToListDTO(List<MovimentoContaCorrenteEntity> inputs) {
		return inputs.stream().map(MovimentoOutputDTO::entityToDto).collect(Collectors.toList());
	}
}
