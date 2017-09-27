package org.apimenov.domclick.interview.ft.rest.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse {

  private String message;

  private ResponseErrorCode code;

  public ExceptionResponse(String message,
      ResponseErrorCode code) {
    this.message = message;
    this.code = code;
  }

  public ExceptionResponse(ResponseErrorCode code) {
    this.code = code;
  }

  public ExceptionResponse(String message) {
    this(ResponseErrorCode.UNEXPECTED_FAILURE);
    this.message = message;
  }
}
