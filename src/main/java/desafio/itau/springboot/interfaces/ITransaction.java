package desafio.itau.springboot.interfaces;

import desafio.itau.springboot.dto.TransactionDTO;

public interface ITransaction {
    void createNewTransaction(TransactionDTO transaction);

    void deleteRecentTransactions();

    void validateTransaction(TransactionDTO transaction);
}
