package br.com.gomes.bankconta.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class ContaPoupancaEntity extends Conta {
	
	@Column(name = "variacao")
	private int variacao;
	
	@JsonIgnore
	@OneToMany(mappedBy = "conta")
	private List<MovimentoEntity> movimentos = new ArrayList<>();
}
