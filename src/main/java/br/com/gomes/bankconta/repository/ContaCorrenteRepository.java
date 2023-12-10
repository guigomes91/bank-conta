package br.com.gomes.bankconta.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gomes.bankconta.entities.cliente.ClienteEntity;
import br.com.gomes.bankconta.entities.conta.ContaCorrenteEntity;

@Repository
public interface ContaCorrenteRepository extends JpaRepository<ContaCorrenteEntity, UUID> {

	Optional<ContaCorrenteEntity> findByNumeroContaAndAgencia(int numeroConta, int agencia);
	Optional<ContaCorrenteEntity> findByCliente(ClienteEntity cliente);
}
