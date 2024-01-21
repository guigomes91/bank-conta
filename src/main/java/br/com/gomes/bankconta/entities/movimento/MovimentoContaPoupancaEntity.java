package br.com.gomes.bankconta.entities.movimento;

import java.util.List;
import java.util.stream.Collectors;

import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import br.com.gomes.bankconta.dto.movimento.MovimentoOutputDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "movimento_conta_poupanca")
public class MovimentoContaPoupancaEntity extends MovimentoEntity {
	
	public static MovimentoContaPoupancaEntity dtoToEntity(MovimentoInputDTO input) {
		MovimentoContaPoupancaEntity entity = new MovimentoContaPoupancaEntity();
		entity.setConta(input.getConta());
		entity.setDataHoraMovimento(input.getDataHoraMovimento());
		entity.setDescricao(input.getDescricao());
		entity.setId(input.getId());
		entity.setNumeroDocumento(input.getNumeroDocumento());
		entity.setTipoMovimento(input.getTipoMovimento());
		entity.setValor(input.getValor());
		
		return entity;
	}
	
	public static MovimentoOutputDTO entityToDTO(MovimentoContaPoupancaEntity input) {
		MovimentoOutputDTO dto = new MovimentoOutputDTO();
		
		dto.setDataHoraMovimento(input.getDataHoraMovimento());
		dto.setDescricao(input.getDescricao());
		dto.setId(input.getId());
		dto.setNumeroDocumento(input.getNumeroDocumento());
		dto.setTipoMovimento(input.getTipoMovimento());
		dto.setValor(input.getValor());
		
		return dto;
	}
	
	public static List<MovimentoOutputDTO> listEntityToList(List<MovimentoContaPoupancaEntity> inputs) {
		return inputs.stream().map(MovimentoOutputDTO::entityToDto).collect(Collectors.toList());
	}
}
