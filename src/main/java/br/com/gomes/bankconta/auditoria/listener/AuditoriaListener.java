package br.com.gomes.bankconta.auditoria.listener;

import br.com.gomes.bankconta.auditoria.Auditavel;
import br.com.gomes.bankconta.auditoria.model.Auditoria;
import br.com.gomes.bankconta.security.UserSS;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDateTime;

public class AuditoriaListener {

    @PrePersist
    public void setCreatedOn(Auditavel auditable) {
        Auditoria audit = auditable.getAudit();

        if (audit == null) {
            audit = new Auditoria();
            auditable.setAudit(audit);
        }

        audit.setCreatedOn(LocalDateTime.now());
        audit.setCreatedBy("guilherme@email.com");
    }

    @PreUpdate
    public void setUpdatedOn(Auditavel auditable) {
        Auditoria audit = auditable.getAudit();

        audit.setUpdatedOn(LocalDateTime.now());
        audit.setUpdatedBy("guilherme@email.com");
    }
}
