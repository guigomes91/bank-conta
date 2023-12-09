package br.com.gomes.bankconta.entities.conta;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.gomes.bankconta.entities.movimento.MovimentoEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class ContaPoupancaEntity extends Conta {
	
	@Column(name = "variacao")
	private int variacao;
	
	@JsonIgnore
	@OneToMany(mappedBy = "conta")
	private List<MovimentoEntity> movimentos = new ArrayList<>();
}
