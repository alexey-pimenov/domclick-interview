package org.apimenov.domclick.interview.ft.service.exception;

public class UserNotFoundException extends EntityNotFoundException {

  public UserNotFoundException() {
  }

  public UserNotFoundException(String message) {
    super(message);
  }
}
