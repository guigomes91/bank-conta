package br.com.gomes.bankconta.entities.movimento;

import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "movimento_conta_corrente")
public class MovimentoContaCorrenteEntity extends MovimentoEntity {
	
	public MovimentoContaCorrenteEntity dtoToEntity(MovimentoInputDTO input) {
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
}
