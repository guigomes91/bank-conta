package br.com.gomes.bankconta.entities.movimento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.gomes.bankconta.entities.conta.ContaEntity;
import br.com.gomes.bankconta.enums.TipoMovimento;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movimento")
public class MovimentoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private LocalDateTime dataHoraMovimento = LocalDateTime.now();
	
	private String numeroDocumento;
	
	private BigDecimal valor = BigDecimal.ZERO;
	
	private String descricao;
	
	private TipoMovimento tipoMovimento;
	
	@ManyToOne
	@JoinColumn(name = "conta_id")
	private ContaEntity conta;
}
