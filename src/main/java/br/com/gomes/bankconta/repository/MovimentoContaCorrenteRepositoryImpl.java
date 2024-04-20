package br.com.gomes.bankconta.repository;

import br.com.gomes.bankconta.entities.movimento.MovimentoContaCorrenteEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;

public class MovimentoContaCorrenteRepositoryImpl {

    @Autowired
    EntityManager em;

    public MovimentoContaCorrenteEntity consultarPorDocumento(long contaCorrente, String numeroDocumento) {
        String sql = """
                select
                    m
                from
                    MovimentoContaCorrenteEntity m join m.conta c
                where
                    m.numeroDocumento = :numeroDocumento and
                    c.numeroConta = :conta
                """;

        TypedQuery<MovimentoContaCorrenteEntity> typedQuery = em.createQuery(sql, MovimentoContaCorrenteEntity.class);
        typedQuery.setParameter("numeroDocumento", numeroDocumento);
        typedQuery.setParameter("conta", contaCorrente);

        return typedQuery.getSingleResult();
    }
}
