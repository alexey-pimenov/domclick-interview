package org.apimenov.domclick.interview.ft.service.exception;

public class AccountNotFoundException extends EntityNotFoundException {

  public AccountNotFoundException(String message) {
    super(message);
  }
}
