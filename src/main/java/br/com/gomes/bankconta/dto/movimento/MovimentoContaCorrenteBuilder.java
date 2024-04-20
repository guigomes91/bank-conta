package br.com.gomes.bankconta.dto.movimento;

import br.com.gomes.bankconta.entities.conta.Conta;
import br.com.gomes.bankconta.entities.movimento.MovimentoContaCorrenteEntity;
import br.com.gomes.bankconta.enums.TipoMovimento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class MovimentoContaCorrenteBuilder {

    private UUID id;

    @JsonIgnore
    private LocalDateTime dataHoraMovimento = LocalDateTime.now();

    @NotNull
    @NotBlank
    private String numeroDocumento;

    private BigDecimal valor = BigDecimal.ZERO;

    @NotNull
    @NotBlank
    private String descricao;

    private TipoMovimento tipoMovimento;

    private Conta conta;

    public MovimentoContaCorrenteBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public MovimentoContaCorrenteBuilder numeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
        return this;
    }

    public MovimentoContaCorrenteBuilder valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public MovimentoContaCorrenteBuilder descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public MovimentoContaCorrenteBuilder tipoMovimento(TipoMovimento tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
        return this;
    }

    public MovimentoContaCorrenteBuilder conta(Conta conta) {
        this.conta = conta;
        return this;
    }

    public MovimentoContaCorrenteEntity build() {
        return new MovimentoContaCorrenteEntity(id, dataHoraMovimento, numeroDocumento, valor, descricao, tipoMovimento, conta);
    }

    public MovimentoInputDTO buildDto() {
        return new MovimentoInputDTO(id, dataHoraMovimento, numeroDocumento, valor, descricao, tipoMovimento, conta);
    }
}
