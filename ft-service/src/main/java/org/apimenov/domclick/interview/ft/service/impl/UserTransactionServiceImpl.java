package org.apimenov.domclick.interview.ft.service.impl;

import java.util.List;
import org.apimenov.domclick.interview.ft.db.domain.entity.Account;
import org.apimenov.domclick.interview.ft.db.domain.entity.Transaction;
import org.apimenov.domclick.interview.ft.db.repo.TransactionRepository;
import org.apimenov.domclick.interview.ft.service.MoneyTransferService;
import org.apimenov.domclick.interview.ft.service.UserAccountService;
import org.apimenov.domclick.interview.ft.service.UserTransactionService;
import org.apimenov.domclick.interview.ft.service.domain.MoneyTransfer;
import org.apimenov.domclick.interview.ft.service.exception.AccountNotFoundException;
import org.apimenov.domclick.interview.ft.service.exception.InsufficientFundsException;
import org.apimenov.domclick.interview.ft.service.exception.TransactionNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserTransactionServiceImpl implements UserTransactionService {


  private TransactionRepository repository;

  private UserAccountService accountService;

  private MoneyTransferService transferService;

  public UserTransactionServiceImpl(
      TransactionRepository repository,
      UserAccountService accountService,
      MoneyTransferService transferService) {
    this.repository = repository;
    this.accountService = accountService;
    this.transferService = transferService;
  }

  @Override
  public List<Transaction> findAll(Long userId, Long accountId) {

    checkUserAccount(userId, accountId);
    return repository.findAllByUserAndAccountId(userId, accountId);
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS)
  public Transaction findOne(Long userId, Long accountId, Long transactionId) {
    checkUserAccount(userId, accountId);

    Transaction transaction = repository.findOne(transactionId);
    if (transaction == null) {
      throw new TransactionNotFoundException("Transaction not found");
    }
    return transaction;
  }

  @Override
  public Transaction processTransfer(MoneyTransfer moneyTransfer)
      throws InsufficientFundsException {
    Account to, from;
    switch (moneyTransfer.getType()) {
      case DEPOSIT:
        to = accountService
            .findAccountForUser(moneyTransfer.getUserId(), moneyTransfer.getToAccountId());

        return transferService.deposit(to, moneyTransfer.getAmount());
      case WITHDRAW:
        from = accountService
            .findAccountForUser(moneyTransfer.getUserId(), moneyTransfer.getFromAccountId());
        return transferService.withdraw(from, moneyTransfer.getAmount());
      case TRANSFER:
        to = accountService
            .findOne(moneyTransfer.getToAccountId());
        from = accountService
            .findAccountForUser(moneyTransfer.getUserId(), moneyTransfer.getFromAccountId());
        return transferService.transfer(from, to, moneyTransfer.getAmount());

      default:
        throw new IllegalArgumentException("won't happen");

    }

  }


  private void checkUserAccount(Long userId, Long accountId) {
    if (!accountService.isUserAccountExists(userId, accountId)) {
      throw new AccountNotFoundException("Account no found");
    }
  }
}
