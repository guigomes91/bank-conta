package br.com.gomes.bankconta.dto.conta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import br.com.gomes.bankconta.entities.cliente.ClienteEntity;
import br.com.gomes.bankconta.entities.conta.ContaCorrenteEntity;
import br.com.gomes.bankconta.enums.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContaCorrenteOutputDTO {

	private UUID id;
	
	private int numeroConta;
	
	private int agencia;
	
	private ClienteEntity cliente;
	
	private BigDecimal saldo;
	
	private TipoConta tipoConta;
	
	private LocalDate dataCriacao;
	
	public ContaCorrenteOutputDTO(ContaCorrenteEntity entity) {
		this.id = entity.getId();
		this.numeroConta = entity.getNumeroConta();
		this.agencia = entity.getAgencia();
		this.cliente = entity.getCliente();
		this.saldo = entity.getSaldo();
		this.tipoConta = entity.getTipoConta();
		this.dataCriacao = entity.getDataCriacao();
	}
}
