package br.com.gomes.bankconta.components;

import br.com.gomes.bankconta.amqp.transacao.EnviaTransacaoContaCorrenteComponent;
import br.com.gomes.bankconta.dto.movimento.MovimentoContaCorrenteBuilder;
import br.com.gomes.bankconta.dto.movimento.MovimentoInputDTO;
import br.com.gomes.bankconta.dto.movimento.MovimentoOutputDTO;
import br.com.gomes.bankconta.dto.movimento.TransferenciaInputDTO;
import br.com.gomes.bankconta.entities.conta.Conta;
import br.com.gomes.bankconta.entities.conta.ContaCorrenteEntity;
import br.com.gomes.bankconta.entities.movimento.MovimentoContaCorrenteEntity;
import br.com.gomes.bankconta.enums.TipoConta;
import br.com.gomes.bankconta.enums.TipoMovimento;
import br.com.gomes.bankconta.repository.MovimentoContaCorrenteRepository;
import br.com.gomes.bankconta.service.impl.Operacao;
import br.com.gomes.bankconta.utils.BankGomesConstantes;
import br.com.gomes.bankconta.validators.ContaValidator;
import br.com.gomes.bankconta.validators.MovimentoValidator;
import br.com.gomes.bankconta.validators.SaldoContaValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Component
public class MovimentoContaCorrenteComponent implements Operacao {

    @Autowired
    private MovimentoContaCorrenteRepository movimentoContaCorrenteRepository;

    @Autowired
    private EnviaTransacaoContaCorrenteComponent enviaTransacaoContaCorrenteComponent;

    @Autowired
    private EnviadorEmailComponent enviadorEmailComponent;

    @Autowired
    private ContaValidator contaValidator;

    @Autowired
    private SaldoContaValidator saldoValidator;

    @Autowired
    private MovimentoValidator movimentoValidator;

    @Autowired
    private MovimentoContaCorrenteBuilder movimentoContaCorrenteBuilder;

    @Override
    @Transactional
    public MovimentoOutputDTO lancarMovimento(MovimentoInputDTO movimentoInputDTO) {
        ContaCorrenteEntity contaCorrenteEntity = contaValidator.contaCorrenteExistente(movimentoInputDTO.getConta().getId());
        MovimentoContaCorrenteEntity movimentoContaCorrenteEntity = MovimentoContaCorrenteEntity.dtoToEntity(movimentoInputDTO);
        MovimentoOutputDTO movimentoOutputDTO = MovimentoOutputDTO.entityToDto(movimentoContaCorrenteRepository.save(movimentoContaCorrenteEntity));

        saldoValidator.movimentarSaldoContaCorrente(contaCorrenteEntity, movimentoInputDTO);

        movimentoInputDTO.setId(movimentoOutputDTO.getId());
        enviaTransacaoContaCorrenteComponent.publicarTransacao(movimentoInputDTO);

        return movimentoOutputDTO;
    }

    @Transactional
    public UUID transferirValorContaCorrente(TransferenciaInputDTO transferenciaInputDTO) {
        var contaCorrenteEntityOrigem = contaValidator
                .existePorAgenciaConta(
                        transferenciaInputDTO.getNumeroContaOrigem(),
                        transferenciaInputDTO.getAgenciaOrigem()
                );
        var contaCorrenteEntityDestino = contaValidator
                .existePorAgenciaConta(
                        transferenciaInputDTO.getNumeroContaDestino(),
                        transferenciaInputDTO.getAgenciaDestino()
                );

        saldoValidator.verificaSaldoNegativo(contaCorrenteEntityOrigem.getSaldo());
        movimentoValidator.validaTransferenciaMesmaConta(contaCorrenteEntityOrigem, contaCorrenteEntityDestino);

        var transaction = UUID.randomUUID();
        var movimentoTransferenciDebito = movimentoContaCorrenteBuilder
                .id(transaction)
                .conta(new ContaCorrenteEntity(contaCorrenteEntityOrigem.getId()))
                .valor(transferenciaInputDTO.getValor())
                .tipoMovimento(TipoMovimento.TRANSFERENCIA_CONTA_CORRENTE_DEBITO)
                .descricao(transferenciaInputDTO.getDescricao())
                .numeroDocumento(String.valueOf(Math.abs(new Random().nextInt())))
                .buildDto();

        saldoValidator.movimentarSaldoContaCorrente(
                contaCorrenteEntityOrigem,
                movimentoTransferenciDebito
        );

        var movimentoTransferenciaCredito = movimentoContaCorrenteBuilder
                .id(transaction)
                .conta(new ContaCorrenteEntity(contaCorrenteEntityDestino.getId()))
                .valor(transferenciaInputDTO.getValor())
                .tipoMovimento(TipoMovimento.TRANSFERENCIA_CONTA_CORRENTE_CREDITO)
                .descricao("Entrada por transferência de conta corrente")
                .numeroDocumento(String.valueOf(Math.abs(new Random().nextInt())))
                .buildDto();

        saldoValidator.movimentarSaldoContaCorrente(
                contaCorrenteEntityDestino,
                movimentoTransferenciaCredito
        );

        var movimentoDebitoOrigemEntity = movimentoContaCorrenteBuilder
                .conta(contaCorrenteEntityOrigem)
                .valor(transferenciaInputDTO.getValor())
                .tipoMovimento(TipoMovimento.TRANSFERENCIA_CONTA_CORRENTE_DEBITO)
                .descricao(transferenciaInputDTO.getDescricao())
                .build();

        var movimentoCreditoDestinoEntity = movimentoContaCorrenteBuilder
                .conta(contaCorrenteEntityDestino)
                .valor(transferenciaInputDTO.getValor())
                .tipoMovimento(TipoMovimento.TRANSFERENCIA_CONTA_CORRENTE_CREDITO)
                .descricao("Entrada por transferência de conta corrente")
                .build();

        movimentoContaCorrenteRepository.saveAll(List.of(movimentoDebitoOrigemEntity, movimentoCreditoDestinoEntity));
        enviaTransacaoContaCorrenteComponent.publicarTransacao(movimentoTransferenciDebito);
        enviaTransacaoContaCorrenteComponent.publicarTransacao(movimentoTransferenciaCredito);
        enviadorEmailComponent.enviarEmailParaCliente(
                contaCorrenteEntityDestino,
                BankGomesConstantes.ASSUNTO_TRANSFERENCIA_ENTRE_CONTAS,
                String.format(BankGomesConstantes.MENSAGEM_TRANSFERENCIA_ENTRE_CONTAS, contaCorrenteEntityOrigem.getCliente().getNome())
        );

        return transaction;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimentoOutputDTO> consultarMovimento(Conta conta) {
        return MovimentoContaCorrenteEntity.listEntityToListDTO(movimentoContaCorrenteRepository.findAll());
    }

    @Override
    public Operacao getInstance() {
        return this;
    }

    @Override
    public TipoConta getTipoOperacao() {
        return TipoConta.CC;
    }
}
