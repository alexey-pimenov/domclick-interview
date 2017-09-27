package org.apimenov.domclick.interview.ft.service.impl.transfer;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.apimenov.domclick.interview.ft.db.domain.TransactionStatus;
import org.apimenov.domclick.interview.ft.db.domain.entity.Account;
import org.apimenov.domclick.interview.ft.db.domain.entity.Transaction;
import org.apimenov.domclick.interview.ft.db.repo.AccountRepository;
import org.apimenov.domclick.interview.ft.db.repo.TransactionRepository;
import org.apimenov.domclick.interview.ft.service.AccountLockingService;
import org.apimenov.domclick.interview.ft.service.exception.InsufficientFundsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Component
@Lazy
public class UserTransactionExecutor {

  private final AccountRepository repository;

  private final AccountLockingService lockingService;

  private final TransactionRepository transactionRepository;

  private final TransactionTemplate transactionTemplate;

  @Autowired
  public UserTransactionExecutor(
      AccountRepository repository,
      AccountLockingService lockingService,
      TransactionRepository transactionRepository,
      PlatformTransactionManager transactionManager) {
    this.repository = repository;
    this.lockingService = lockingService;

    transactionTemplate = new TransactionTemplate(transactionManager,
        new DefaultTransactionDefinition(
            TransactionDefinition.PROPAGATION_REQUIRES_NEW));

    this.transactionRepository = transactionRepository;
  }


  @Async
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = InsufficientFundsException.class)
  public CompletableFuture<List<Transaction>> run(
      List<Transaction> transactions) throws InsufficientFundsException {

    List<Account> accounts = transactions.stream().map(Transaction::getAccount)
        .collect(Collectors.toList());

    lockingService.lock(accounts);

    try {
      Thread.sleep(60000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    try {
      for (Transaction tr : transactions) {
        if (tr.getAccount().getBalance().add(tr.getAmount()).compareTo(BigDecimal.ZERO) < 0) {

          transactionTemplate.execute(
              (TransactionCallback<Object>) status -> updateStatus(transactions,
                  TransactionStatus.FAILED));
          throw new InsufficientFundsException(tr.getAccount());
        }
        repository.updateBalance(tr.getAccount(), tr.getAmount());

      }
      return CompletableFuture
          .completedFuture(updateStatus(transactions, TransactionStatus.APPROVED));

    } finally {
      lockingService.release(accounts);
    }


  }


  private List<Transaction> updateStatus(List<Transaction> transactions, TransactionStatus status) {
    for (Transaction tr : transactions) {
      tr.setStatus(status);

    }
    return transactionRepository.save(transactions);
  }

}
