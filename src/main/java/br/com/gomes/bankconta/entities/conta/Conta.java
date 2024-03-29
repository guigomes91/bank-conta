package br.com.gomes.bankconta.entities.conta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import br.com.gomes.bankconta.entities.cliente.ClienteEntity;
import br.com.gomes.bankconta.enums.SituacaoConta;
import br.com.gomes.bankconta.enums.TipoConta;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public abstract class Conta {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private int numeroConta;
	private int agencia;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private ClienteEntity cliente;
	
	private BigDecimal saldo;
	private TipoConta tipoConta;

	@JsonIgnore
	private LocalDate dataCriacao = LocalDate.now();

	private SituacaoConta situacaoConta;
	
}
