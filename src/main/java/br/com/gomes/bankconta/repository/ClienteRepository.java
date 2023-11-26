package br.com.gomes.bankconta.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gomes.bankconta.entities.ClienteEntity;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, UUID> {

	Optional<ClienteEntity> findByCpf(long cpf);
	Optional<ClienteEntity> findByEmail(String email);	
}
