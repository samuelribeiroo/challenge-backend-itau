package desafio.itau.springboot.interfaces;

import desafio.itau.springboot.dto.TransactionDTO;

import java.util.Optional;

public interface ITransaction {
    void createNewTransaction(TransactionDTO transaction);

    Optional<DeletionStatus> deleteRecentTransactions();

    void validateTransaction(TransactionDTO transaction);
}
