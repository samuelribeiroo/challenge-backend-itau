package desafio.itau.springboot.services;

import desafio.itau.springboot.dto.TransactionDTO;
import desafio.itau.springboot.interfaces.DeletionStatus;
import desafio.itau.springboot.interfaces.ITransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
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
          log.info("Caso não houver deleções a serem feitas e o usuário fazer nova tentativa o endpoint de deleção vai mostrar o status code 400");
          return Optional.of(DeletionStatus.ALREADY_EMPTY);
      }

      transactionStore.clear();

      log.info("Todas as transações removidas: " + transactionStore.size());

      return Optional.of(DeletionStatus.SUCCESS);
    }

    @Override
    public void validateTransaction(TransactionDTO transaction) {
        if (transaction.getValor().compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Valor da transação inválido!");

        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime transactionDateTime = transaction.getDataHora();

        /*   Tolerance of up to 1 second before or after.  */
        boolean isValidDateTime = transactionDateTime.isBefore(now.minusSeconds(1)) || transactionDateTime.isAfter(now.plusSeconds(1));

        String errorMessage = MessageFormat.format("Data inválida para transação: {0}. Data Atual: {1} Analise se data/horário é correto.", transactionDateTime, now);

        if (isValidDateTime) throw new IllegalArgumentException(errorMessage);
    }

    @Override
    public List<TransactionDTO> getRecentTransactions() {
        return transactionStore;
    }
}
