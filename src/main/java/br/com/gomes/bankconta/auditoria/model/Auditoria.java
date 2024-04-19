package br.com.gomes.bankconta.auditoria.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Embeddable
public class Auditoria {

    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @JsonIgnore
    @Column(name = "created_on", updatable = false)
    private LocalDateTime createdOn;

    @Column(name = "updated_by", insertable = false)
    private String updatedBy;

    @JsonIgnore
    @Column(name = "updated_on", insertable = false)
    private LocalDateTime updatedOn;
}
