package org.apimenov.domclick.interview.ft.service.exception;

public class FtRuntimeException extends RuntimeException {

  public FtRuntimeException() {
  }

  public FtRuntimeException(String message) {
    super(message);
  }

  public FtRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }
}
