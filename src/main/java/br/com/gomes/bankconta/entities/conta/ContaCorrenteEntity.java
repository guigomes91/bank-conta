package br.com.gomes.bankconta.entities.conta;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.gomes.bankconta.entities.movimento.MovimentoEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class ContaCorrenteEntity extends Conta {
	
	@JsonIgnore
	@OneToMany(mappedBy = "conta")
	private List<MovimentoEntity> movimentos = new ArrayList<>();
}
