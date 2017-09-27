package org.apimenov.domclick.interview.ft.service;

import java.util.List;
import org.apimenov.domclick.interview.ft.db.domain.entity.Transaction;
import org.apimenov.domclick.interview.ft.service.domain.MoneyTransfer;
import org.apimenov.domclick.interview.ft.service.exception.InsufficientFundsException;

public interface UserTransactionService {

  List<Transaction> findAll(Long userId, Long accountId);

  Transaction findOne(Long userId, Long accountId, Long transactionId);


  Transaction processTransfer(MoneyTransfer moneyTransfer) throws InsufficientFundsException;


}
