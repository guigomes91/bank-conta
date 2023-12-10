package br.com.gomes.bankconta.entities.conta;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.gomes.bankconta.dto.conta.ContaCorrenteInputDTO;
import br.com.gomes.bankconta.entities.movimento.MovimentoEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ContaCorrenteEntity extends Conta {
	
	@JsonIgnore
	@OneToMany(mappedBy = "conta")
	private List<MovimentoEntity> movimentos = new ArrayList<>();
	
	public ContaCorrenteEntity dtoToEntity(ContaCorrenteInputDTO ccDTO) {
		ContaCorrenteEntity ccEntity = new ContaCorrenteEntity();
		ccEntity.setId(ccDTO.getId());
		ccEntity.setAgencia(ccDTO.getAgencia());
		ccEntity.setCliente(ccDTO.getCliente());
		ccEntity.setNumeroConta(ccDTO.getNumeroConta());
		ccEntity.setSaldo(ccDTO.getSaldo());
		ccEntity.setTipoConta(ccDTO.getTipoConta());
		
		return ccEntity;
	}
}
