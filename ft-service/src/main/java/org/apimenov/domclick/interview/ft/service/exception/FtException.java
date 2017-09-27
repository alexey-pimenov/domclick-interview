package org.apimenov.domclick.interview.ft.service.exception;

public class FtException extends Exception {

  public FtException() {
  }

  public FtException(String message) {
    super(message);
  }

  public FtException(String message, Throwable cause) {
    super(message, cause);
  }

  public FtException(Throwable cause) {
    super(cause);
  }
}
