package br.com.gomes.bankconta.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gomes.bankconta.entities.conta.ContaPoupancaEntity;
import br.com.gomes.bankconta.entities.movimento.MovimentoContaPoupancaEntity;

@Repository
public interface MovimentoContaPoupancaRepository extends JpaRepository<MovimentoContaPoupancaEntity, UUID> {

	List<MovimentoContaPoupancaEntity> findByConta(ContaPoupancaEntity conta, Pageable pageable);
}
