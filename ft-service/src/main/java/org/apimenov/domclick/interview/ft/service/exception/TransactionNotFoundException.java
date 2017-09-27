package org.apimenov.domclick.interview.ft.service.exception;

public class TransactionNotFoundException extends EntityNotFoundException {

  public TransactionNotFoundException() {
  }

  public TransactionNotFoundException(String message) {
    super(message);
  }
}
