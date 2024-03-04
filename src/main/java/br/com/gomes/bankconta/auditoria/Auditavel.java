package br.com.gomes.bankconta.auditoria;

import br.com.gomes.bankconta.auditoria.model.Auditoria;

public interface Auditavel {

    Auditoria getAudit();

    void setAudit(Auditoria audit);
}
