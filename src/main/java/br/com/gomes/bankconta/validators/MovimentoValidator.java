package br.com.gomes.bankconta.validators;

import br.com.gomes.bankconta.entities.conta.ContaCorrenteEntity;
import br.com.gomes.bankconta.resources.exceptions.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
public class MovimentoValidator {
    public void validaTransferenciaMesmaConta(ContaCorrenteEntity contaCorrenteOrigem, ContaCorrenteEntity contaCorrenteDestino) {
        if (contaCorrenteOrigem.getNumeroConta() == contaCorrenteDestino.getNumeroConta() &&
            contaCorrenteOrigem.getAgencia() == contaCorrenteDestino.getAgencia()) {
            throw new BadRequestException("Operação não permitida, as contas devem ser diferentes!");
        }
    }
}
