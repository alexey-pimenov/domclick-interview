package org.apimenov.domclick.interview.ft.service.exception;

public class EntityNotFoundException extends FtRuntimeException {

  public EntityNotFoundException() {
  }

  public EntityNotFoundException(String message) {
    super(message);
  }
}
