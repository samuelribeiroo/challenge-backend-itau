package desafio.itau.springboot.services;

import desafio.itau.springboot.dto.TransactionDTO;
import desafio.itau.springboot.interfaces.DeletionStatus;
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
    public Optional<DeletionStatus> deleteRecentTransactions() {
      if(transactionStore.isEmpty()) {
          log.info("Não existe transações para remover: " + transactionStore.size());
          return Optional.of(DeletionStatus.ALREADY_EMPTY);
      }

      transactionStore.clear();

      log.info("Todas as transações removidas: " + transactionStore.size());

      return Optional.of(DeletionStatus.SUCCESS);
    }

    @Override
    public void validateTransaction(TransactionDTO transaction) {
        if (transaction.getValor().compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Valor da transação inválido!");

        boolean isValidDate = transaction.getDataHora().isAfter(OffsetDateTime.now()) || transaction.getDataHora() != OffsetDateTime.now();

        if (isValidDate) {
            throw new IllegalArgumentException("Data da transação inválida");
        }
    }
}
