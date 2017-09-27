package org.apimenov.domclick.interview.ft.service.impl.transfer;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.apimenov.domclick.interview.ft.db.domain.TransactionStatus;
import org.apimenov.domclick.interview.ft.db.domain.entity.Account;
import org.apimenov.domclick.interview.ft.db.domain.entity.Transaction;
import org.apimenov.domclick.interview.ft.db.repo.AccountRepository;
import org.apimenov.domclick.interview.ft.db.repo.TransactionRepository;
import org.apimenov.domclick.interview.ft.service.exception.InsufficientFundsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AsyncMoneyTransferService extends SimpleMoneyTransferService {

  private UserTransactionExecutor executor;


  @Autowired
  public AsyncMoneyTransferService(
      TransactionRepository repository,
      AccountRepository accountRepository, UserTransactionExecutor executor) {
    super(repository, accountRepository);
    this.executor = executor;
  }

  @Override
  public TransactionStatus getDefaultStatus() {
    return TransactionStatus.PENDING;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Transaction withdraw(Account account, BigDecimal amount)
      throws InsufficientFundsException {
    Transaction transaction = prepareTransaction(account, amount.negate());
    transactionRepository.save(transaction);

    CompletableFuture<List<Transaction>> future = executor
        .run(Collections.singletonList(transaction));

    return transaction;


  }


  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Transaction deposit(Account account, BigDecimal amount)
      throws InsufficientFundsException {
    Transaction transaction = prepareTransaction(account, amount);
    transactionRepository.save(transaction);

    CompletableFuture<List<Transaction>> future = executor
        .run(Collections.singletonList(transaction));

    return transaction;


  }

  @Override
  public Transaction transfer(Account from, Account to, BigDecimal amount)
      throws InsufficientFundsException {
    Transaction transactionFrom = prepareTransaction(from, amount.negate());
    transactionRepository.save(transactionFrom);

    Transaction transactionTo = prepareTransaction(to, amount);
    transactionRepository.save(transactionTo);

    CompletableFuture<List<Transaction>> future = executor
        .run(Arrays.asList(transactionFrom, transactionTo));

    return transactionFrom;
  }
}
