package br.com.gomes.bankconta.components;

import br.com.gomes.bankconta.amqp.notificacao.EnviaEmailComponent;
import br.com.gomes.bankconta.dto.cliente.ClienteDTO;
import br.com.gomes.bankconta.entities.cliente.ClienteEntity;
import br.com.gomes.bankconta.entities.conta.ContaCorrenteEntity;
import br.com.gomes.bankconta.validators.ClienteValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EnviadorEmailComponent {

    @Autowired
    private ClienteValidator clienteValidator;

    @Autowired
    private EnviaEmailComponent emailComponente;

    public void enviarEmailParaCliente(ContaCorrenteEntity contaCorrenteEntity, String assunto, String mensagem) {
        ClienteEntity clienteEntity = null;
        try {
            clienteEntity = clienteValidator
                    .verificaClienteExistente(contaCorrenteEntity.getCliente().getId());

            emailComponente.enviarEmail(
                    new ClienteDTO(clienteEntity),
                    assunto,
                    mensagem);
        } catch (RuntimeException ex) {
            log.error("Não foi possível enviar email para o cliente {} -> Error {}", clienteEntity, ex.getMessage());
        }
    }
}
