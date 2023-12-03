package br.com.gomes.bankconta.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gomes.bankconta.entities.ClienteEntity;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, UUID> {

	Optional<ClienteEntity> findByCpf(long cpf);

	Optional<ClienteEntity> findByEmail(String email);

	@Query("FROM ClienteEntity c WHERE LOWER(c.nome) like %:searchTerm% OR LOWER(c.email) like %:searchTerm%")
	Page<ClienteEntity> consultarPorNomeOuEmail(@Param("searchTerm") String searchTerm, Pageable pageable);
}
