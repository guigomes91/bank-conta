package br.com.gomes.bankconta.entities.conta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import br.com.gomes.bankconta.auditoria.Auditavel;
import br.com.gomes.bankconta.auditoria.listener.AuditoriaListener;
import br.com.gomes.bankconta.auditoria.model.Auditoria;
import br.com.gomes.bankconta.entities.cliente.ClienteEntity;
import br.com.gomes.bankconta.enums.SituacaoConta;
import br.com.gomes.bankconta.enums.TipoConta;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@EntityListeners(AuditoriaListener.class)
public abstract class Conta implements Auditavel {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "numero_conta", nullable = false)
	private int numeroConta;

	@Column(name = "agencia", nullable = false)
	private int agencia;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private ClienteEntity cliente;
	
	private BigDecimal saldo;

	@Enumerated(EnumType.STRING)
	private TipoConta tipoConta;

	@JsonIgnore
	private LocalDate dataCriacao = LocalDate.now();

	@Enumerated(EnumType.STRING)
	private SituacaoConta situacaoConta;

	@Embedded
	private Auditoria auditoria;

	public Conta(UUID id) {
		this.id = id;
	}

	@Override
	public Auditoria getAudit() {
		return this.auditoria;
	}

	@Override
	public void setAudit(Auditoria auditoria) {
		this.auditoria = auditoria;
	}
}
