package org.apimenov.domclick.interview.ft.rest.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.apimenov.domclick.interview.ft.db.domain.entity.Transaction;
import org.apimenov.domclick.interview.ft.rest.domain.ExceptionResponse;
import org.apimenov.domclick.interview.ft.rest.domain.ResponseErrorCode;
import org.apimenov.domclick.interview.ft.rest.domain.dto.TransactionDTO;
import org.apimenov.domclick.interview.ft.rest.domain.dto.TransferDTO;
import org.apimenov.domclick.interview.ft.service.UserTransactionService;
import org.apimenov.domclick.interview.ft.service.domain.MoneyTransfer;
import org.apimenov.domclick.interview.ft.service.exception.InsufficientFundsException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/users/{userId}/accounts/{accountId}/transactions")
public class TransactionController {

  private UserTransactionService service;

  private ModelMapper mapper;


  public TransactionController(
      UserTransactionService service, ModelMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @GetMapping
  public List<TransactionDTO> getAll(
      @PathVariable("userId") Long userId,
      @PathVariable("accountId") Long accountId) {

    return service.findAll(userId, accountId)
        .stream().map(e -> mapper.map(e, TransactionDTO.class))
        .collect(Collectors.toList());

  }


  @GetMapping("/{transactionId}")
  public TransactionDTO getOne(
      @PathVariable("userId") Long userId,
      @PathVariable("accountId") Long accountId,
      @PathVariable("transactionId") Long transactionId) {

    Transaction transaction = service.findOne(userId, accountId, transactionId);

    return mapper.map(transaction, TransactionDTO.class);

  }


  @PostMapping
  public ResponseEntity<TransactionDTO> transferMoney(
      @PathVariable("userId") Long userId,
      @PathVariable("accountId") Long accountId,
      @RequestBody @Valid TransferDTO dto,
      UriComponentsBuilder b)
      throws InsufficientFundsException {
    MoneyTransfer transfer = mapper.map(dto, MoneyTransfer.class);
    transfer.setFromAccountId(accountId);
    transfer.setUserId(userId);
    TransactionDTO transactionDTO = mapper
        .map(service.processTransfer(transfer), TransactionDTO.class);
    UriComponents uriComponents = b
        .path("/users/{userId}/accounts/{accountId}/transactions/{transactionId}")
        .buildAndExpand(userId, accountId, transactionDTO.getId());

    return ResponseEntity.created(uriComponents.toUri()).body(transactionDTO);

  }

  @ExceptionHandler(InsufficientFundsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse handle(InsufficientFundsException e) {
    return new ExceptionResponse("Insufficient funds on account: " + e.getAccountId(),
        ResponseErrorCode.INSUFFICIENT_FUNDS);
  }


}
