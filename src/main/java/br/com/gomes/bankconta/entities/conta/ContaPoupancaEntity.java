package br.com.gomes.bankconta.entities.conta;

import org.springframework.data.domain.Page;

import br.com.gomes.bankconta.dto.conta.ContaPoupancaInputDTO;
import br.com.gomes.bankconta.dto.conta.ContaPoupancaOutputDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class ContaPoupancaEntity extends Conta {
	
	@Column(name = "variacao")
	private int variacao;

	public ContaPoupancaEntity dtoToEntity(ContaPoupancaInputDTO contaPoupancaInputDTO) {
		var contaPoupancaEntity = new ContaPoupancaEntity();
		
		contaPoupancaEntity.setId(contaPoupancaInputDTO.getId());
		contaPoupancaEntity.setAgencia(contaPoupancaInputDTO.getAgencia());
		contaPoupancaEntity.setCliente(contaPoupancaInputDTO.getCliente());
		contaPoupancaEntity.setNumeroConta(contaPoupancaInputDTO.getNumeroConta());
		contaPoupancaEntity.setSaldo(contaPoupancaInputDTO.getSaldo());
		contaPoupancaEntity.setTipoConta(contaPoupancaInputDTO.getTipoConta());
		contaPoupancaEntity.setVariacao(contaPoupancaInputDTO.getVariacao());
		
		return contaPoupancaEntity;
	}

	public static Page<ContaPoupancaOutputDTO> entityPageToOutputDTO(Page<ContaPoupancaEntity> contaPoupancaEntity) {
		return contaPoupancaEntity.map(contaEntity -> new ContaPoupancaOutputDTO(contaEntity));
	}
}
