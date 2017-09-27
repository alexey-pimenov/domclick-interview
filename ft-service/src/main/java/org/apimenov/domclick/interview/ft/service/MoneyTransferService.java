package org.apimenov.domclick.interview.ft.service;


import java.math.BigDecimal;
import org.apimenov.domclick.interview.ft.db.domain.TransactionStatus;
import org.apimenov.domclick.interview.ft.db.domain.entity.Account;
import org.apimenov.domclick.interview.ft.db.domain.entity.Transaction;
import org.apimenov.domclick.interview.ft.service.exception.InsufficientFundsException;

public interface MoneyTransferService {


  Transaction withdraw(Account account, BigDecimal amount) throws InsufficientFundsException;

  Transaction deposit(Account account, BigDecimal amount) throws InsufficientFundsException;

  Transaction transfer(Account from, Account to, BigDecimal amount)
      throws InsufficientFundsException;


  TransactionStatus getDefaultStatus();


}
