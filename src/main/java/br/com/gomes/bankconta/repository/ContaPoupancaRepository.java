package br.com.gomes.bankconta.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gomes.bankconta.entities.cliente.ClienteEntity;
import br.com.gomes.bankconta.entities.conta.ContaPoupancaEntity;

@Repository
public interface ContaPoupancaRepository extends JpaRepository<ContaPoupancaEntity, UUID> {

	Optional<ContaPoupancaEntity> findByNumeroContaAndAgenciaAndVariacao(int numeroConta, int agencia, int variacao);
	Optional<ContaPoupancaEntity> findByNumeroConta(int numeroConta);
	Optional<ContaPoupancaEntity> findByCliente(ClienteEntity cliente);
	
	Page<ContaPoupancaEntity> findByDataCriacaoBetween(LocalDate dataInicio, LocalDate dataTermino);
}
