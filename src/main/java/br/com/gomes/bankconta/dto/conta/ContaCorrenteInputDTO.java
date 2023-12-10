package br.com.gomes.bankconta.dto.conta;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.gomes.bankconta.entities.cliente.ClienteEntity;
import br.com.gomes.bankconta.entities.conta.ContaCorrenteEntity;
import br.com.gomes.bankconta.enums.TipoConta;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContaCorrenteInputDTO {

	private UUID id;
	
	private int numeroConta;
	
	private int agencia;
	
	@NotNull
	private ClienteEntity cliente;
	
	private BigDecimal saldo;
	
	private TipoConta tipoConta = TipoConta.CC;
	
	public ContaCorrenteInputDTO(ContaCorrenteEntity entity) {
		this.id = entity.getId();
		this.numeroConta = entity.getNumeroConta();
		this.agencia = entity.getAgencia();
		this.cliente = entity.getCliente();
		this.saldo = entity.getSaldo();
	}
}
