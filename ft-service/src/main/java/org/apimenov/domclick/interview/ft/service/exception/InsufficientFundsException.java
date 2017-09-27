package org.apimenov.domclick.interview.ft.service.exception;

import lombok.Getter;
import org.apimenov.domclick.interview.ft.db.domain.entity.Account;

public class InsufficientFundsException extends FtException {

  @Getter
  private Long accountId;

  public InsufficientFundsException(Long accountId) {
    super("Insufficient funds on account with ID: " + accountId);
    this.accountId = accountId;

  }

  public InsufficientFundsException(Account account) {
    this(account.getId());

  }

}
