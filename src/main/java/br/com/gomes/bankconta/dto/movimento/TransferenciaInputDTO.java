package br.com.gomes.bankconta.dto.movimento;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaInputDTO {

    @NotNull(message = "O número da conta origem é obrigatório")
    private int numeroContaOrigem;

    @NotNull(message = "O número da agencia de origem é obrigatório")
    private int agenciaOrigem;

    @NotNull(message = "O número da conta destino é obrigatório")
    private int numeroContaDestino;

    @NotNull(message = "O número da agencia de destino é obrigatório")
    private int agenciaDestino;

    private String descricao;

    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
    @NotNull
    private BigDecimal valor;
}
