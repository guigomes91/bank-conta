package br.com.gomes.bankconta.entities.movimento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.gomes.bankconta.entities.conta.Conta;
import br.com.gomes.bankconta.enums.TipoMovimento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movimento")
public class MovimentoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "data_hora_movimento", nullable = false)
	private LocalDateTime dataHoraMovimento = LocalDateTime.now();

	@Column(name = "numero_documento", length = 10, nullable = false)
	private String numeroDocumento;

	@Column(name = "valor", nullable = false)
	private BigDecimal valor = BigDecimal.ZERO;
	
	private String descricao;

	@Enumerated(EnumType.STRING)
	private TipoMovimento tipoMovimento;
	
	@ManyToOne(optional = false)
	@JoinColumn(
			name = "conta_id",
			nullable = false,
			foreignKey = @ForeignKey(name = "fk_movimento_conta")
	)
	private Conta conta;
}
