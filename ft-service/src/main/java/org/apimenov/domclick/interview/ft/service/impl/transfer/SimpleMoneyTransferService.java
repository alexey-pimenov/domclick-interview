package org.apimenov.domclick.interview.ft.service.impl.transfer;

import java.math.BigDecimal;
import org.apimenov.domclick.interview.ft.db.domain.TransactionStatus;
import org.apimenov.domclick.interview.ft.db.domain.entity.Account;
import org.apimenov.domclick.interview.ft.db.domain.entity.Transaction;
import org.apimenov.domclick.interview.ft.db.repo.AccountRepository;
import org.apimenov.domclick.interview.ft.db.repo.TransactionRepository;
import org.apimenov.domclick.interview.ft.service.MoneyTransferService;
import org.apimenov.domclick.interview.ft.service.exception.InsufficientFundsException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class SimpleMoneyTransferService implements MoneyTransferService {

  protected TransactionRepository transactionRepository;

  protected AccountRepository accountRepository;

  public SimpleMoneyTransferService(
      TransactionRepository transactionRepository,
      AccountRepository accountRepository) {
    this.transactionRepository = transactionRepository;
    this.accountRepository = accountRepository;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Transaction withdraw(Account account, BigDecimal amount)
      throws InsufficientFundsException {
    if (amount.compareTo(account.getBalance()) >= 0) {
      throw new InsufficientFundsException(account);
    }
    Transaction transaction = prepareTransaction(account, amount.negate());

    processTransaction(transaction);
    return transaction;


  }


  public Transaction prepareTransaction(Account account, BigDecimal amount)
      throws InsufficientFundsException {

    Transaction transaction = new Transaction();
    transaction.setAccount(account);
    transaction.setAmount(amount);
    transaction.setStatus(getDefaultStatus());
    return transaction;

  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Transaction deposit(Account account, BigDecimal amount) throws InsufficientFundsException {

    Transaction transaction = prepareTransaction(account, amount);

    processTransaction(transaction);

    return transaction;


  }


  protected void processTransaction(Transaction transaction) {
    transactionRepository.save(transaction);

    accountRepository.updateBalance(transaction.getAccount(), transaction.getAmount());
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Transaction transfer(Account from, Account to, BigDecimal amount)
      throws InsufficientFundsException {
    Transaction tr = withdraw(from, amount);
    deposit(to, amount);
    return tr;
  }

  @Override
  public TransactionStatus getDefaultStatus() {
    return TransactionStatus.APPROVED;
  }
}
