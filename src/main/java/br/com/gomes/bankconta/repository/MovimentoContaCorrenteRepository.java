package br.com.gomes.bankconta.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gomes.bankconta.entities.movimento.MovimentoContaCorrenteEntity;

@Repository
public interface MovimentoContaCorrenteRepository extends JpaRepository<MovimentoContaCorrenteEntity, UUID> {

	MovimentoContaCorrenteEntity findByDescricao(String descricao);
}
