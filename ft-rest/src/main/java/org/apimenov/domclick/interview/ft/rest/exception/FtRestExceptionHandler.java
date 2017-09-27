package org.apimenov.domclick.interview.ft.rest.exception;

import org.apimenov.domclick.interview.ft.rest.domain.ExceptionResponse;
import org.apimenov.domclick.interview.ft.rest.domain.ResponseErrorCode;
import org.apimenov.domclick.interview.ft.service.exception.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class FtRestExceptionHandler extends ResponseEntityExceptionHandler {


  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<?> handleNotFound(EntityNotFoundException ex, WebRequest request) {
    return handleExceptionInternal(ex,
        new ExceptionResponse(ex.getMessage(), ResponseErrorCode.NOT_FOUND),
        new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }


}
