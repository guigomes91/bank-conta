package br.com.gomes.bankconta.components;

import br.com.gomes.bankconta.amqp.transacao.EnviaTransacaoContaCorrenteComponent;
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
    private MovimentoContaCorrenteRepository movRepository;

    @Autowired
    private EnviaTransacaoContaCorrenteComponent enviaTransacaoContaCorrenteComponent;

    @Autowired
    private EnviadorEmailComponent enviadorEmailComponent;

    @Autowired
    private ContaValidator ccValidator;

    @Autowired
    private SaldoContaValidator saldoValidator;

    @Autowired
    private MovimentoValidator movimentoValidator;

    @Override
    @Transactional
    public MovimentoOutputDTO lancarMovimento(MovimentoInputDTO movimento) {
        ContaCorrenteEntity contaCorrenteEntity = ccValidator.contaCorrenteExistente(movimento.getConta().getId());
        MovimentoContaCorrenteEntity entity = MovimentoContaCorrenteEntity.dtoToEntity(movimento);
        MovimentoOutputDTO movimentoOutputDTO = MovimentoOutputDTO.entityToDto(movRepository.save(entity));

        saldoValidator.movimentarSaldoContaCorrente(contaCorrenteEntity, movimento);

        movimento.setId(movimentoOutputDTO.getId());
        enviaTransacaoContaCorrenteComponent.publicarTransacao(movimento);

        return movimentoOutputDTO;
    }

    @Transactional
    public UUID transferirContaCorrente(TransferenciaInputDTO transferenciaInputDTO) {
        MovimentoContaCorrenteEntity movEntityDebitoOrigem = new MovimentoContaCorrenteEntity();
        MovimentoContaCorrenteEntity movEntityCreditoDestino = new MovimentoContaCorrenteEntity();
        var contaCorrenteEntityOrigem = ccValidator.existePorAgenciaConta(transferenciaInputDTO.getNumeroContaOrigem(), transferenciaInputDTO.getAgenciaOrigem());
        var contaCorrenteEntityDestino = ccValidator.existePorAgenciaConta(transferenciaInputDTO.getNumeroContaDestino(), transferenciaInputDTO.getAgenciaDestino());

        //Conta origem
        movEntityDebitoOrigem.setConta(contaCorrenteEntityOrigem);
        movEntityDebitoOrigem.setValor(transferenciaInputDTO.getValor());
        movEntityDebitoOrigem.setTipoMovimento(TipoMovimento.TRANSFERENCIA_CONTA_CORRENTE_DEBITO);
        movEntityDebitoOrigem.setDescricao(transferenciaInputDTO.getDescricao());

        //Conta destino
        movEntityCreditoDestino.setConta(contaCorrenteEntityDestino);
        movEntityCreditoDestino.setValor(transferenciaInputDTO.getValor());
        movEntityCreditoDestino.setTipoMovimento(TipoMovimento.TRANSFERENCIA_CONTA_CORRENTE_CREDITO);
        movEntityCreditoDestino.setDescricao("Entrada por transferência de conta corrente");

        saldoValidator.verificaSaldoNegativo(contaCorrenteEntityOrigem.getSaldo());
        movimentoValidator.validaTransferenciaMesmaConta(contaCorrenteEntityOrigem, contaCorrenteEntityDestino);

        var movimentoTransferenciDebito = new MovimentoInputDTO(
                TipoMovimento.TRANSFERENCIA_CONTA_CORRENTE_DEBITO,
                transferenciaInputDTO.getValor()
        );
        movimentoTransferenciDebito.setConta(contaCorrenteEntityOrigem);
        movimentoTransferenciDebito.setNumeroDocumento(String.valueOf(Math.abs(new Random().nextInt())));
        movimentoTransferenciDebito.setDescricao(transferenciaInputDTO.getDescricao());
        saldoValidator.movimentarSaldoContaCorrente(contaCorrenteEntityOrigem,
                movimentoTransferenciDebito);

        var movimentoTransferenciaCredito = new MovimentoInputDTO(
                TipoMovimento.TRANSFERENCIA_CONTA_CORRENTE_CREDITO,
                transferenciaInputDTO.getValor()
        );
        movimentoTransferenciaCredito.setConta(contaCorrenteEntityOrigem);
        movimentoTransferenciaCredito.setNumeroDocumento(String.valueOf(Math.abs(new Random().nextInt())));
        movimentoTransferenciaCredito.setDescricao("Entrada por transferência de conta corrente");
        saldoValidator.movimentarSaldoContaCorrente(contaCorrenteEntityDestino,
                movimentoTransferenciaCredito);
        movRepository.saveAll(List.of(movEntityDebitoOrigem, movEntityCreditoDestino));

        enviaTransacaoContaCorrenteComponent.publicarTransacao(movimentoTransferenciDebito);
        enviaTransacaoContaCorrenteComponent.publicarTransacao(movimentoTransferenciaCredito);

        enviadorEmailComponent.enviarEmailParaCliente(
                contaCorrenteEntityDestino,
                BankGomesConstantes.ASSUNTO_TRANSFERENCIA_ENTRE_CONTAS,
                String.format(BankGomesConstantes.MENSAGEM_TRANSFERENCIA_ENTRE_CONTAS, contaCorrenteEntityOrigem.getCliente().getNome())
        );

        return UUID.randomUUID();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimentoOutputDTO> consultarMovimento(Conta conta) {
        return MovimentoContaCorrenteEntity.listEntityToListDTO(movRepository.findAll());
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
