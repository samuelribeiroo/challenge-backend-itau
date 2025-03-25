package desafio.itau.springboot.services;

import desafio.itau.springboot.dto.TransactionDTO;
import desafio.itau.springboot.interfaces.ITransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

@Service
public class TransactionService implements ITransaction {

    private final List<TransactionDTO> transactionStore = new ArrayList<>();

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    @Override
    public void createNewTransaction(TransactionDTO transaction) {
        validateTransaction(transaction);

        transactionStore.add(transaction);
        log.info( "Transação adicionada com sucesso: " + transactionStore.size());
    }

    @Override
    public void deleteRecentTransactions() {
        transactionStore.clear();
        log.info( "Transação removida: " + transactionStore.size());
    }

    @Override
    public void validateTransaction(TransactionDTO transaction) {
        if (transaction.getValor().compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Valor da transação inválido!");

        if (transaction.getDataHora().isAfter(OffsetDateTime.now())) throw new IllegalArgumentException("Data da transação inválida");

     if(transaction.getDataHora() != OffsetDateTime.now()) throw new IllegalArgumentException("Data da transação inválida");
    }
}
