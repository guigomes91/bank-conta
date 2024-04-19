package br.com.gomes.bankconta.dto.movimento;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TransferenciaOutputDTO {

    private UUID identificador;
    private String mensagem;
}
