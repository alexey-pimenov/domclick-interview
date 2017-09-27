package org.apimenov.domclick.interview.ft.rest.controller;


import java.util.List;
import java.util.stream.Collectors;
import org.apimenov.domclick.interview.ft.db.domain.entity.Account;
import org.apimenov.domclick.interview.ft.rest.domain.dto.AccountDTO;
import org.apimenov.domclick.interview.ft.rest.domain.dto.NewAccountDTO;
import org.apimenov.domclick.interview.ft.service.UserAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/users/{userId}/accounts")
public class UserAccountController {


  private ModelMapper mapper;

  private UserAccountService service;


  public UserAccountController(ModelMapper mapper,
      UserAccountService service) {
    this.mapper = mapper;
    this.service = service;
  }

  @GetMapping
  public List<AccountDTO> getAll(@PathVariable("userId") Long userId) {
    return service
        .findAllUserAccounts(userId)
        .stream().map(e -> mapper.map(e, AccountDTO.class))
        .collect(Collectors.toList());
  }


  @PostMapping
  public ResponseEntity<?> create(@PathVariable("userId") Long userId,
      @RequestBody @Validated NewAccountDTO dto,
      UriComponentsBuilder b) {

    Account entity = service.createAccountForUser(userId, mapper.map(dto, Account.class));

    UriComponents uriComponents =
        b.path("/users/{userId}/accounts/{accountId}").buildAndExpand(userId, entity.getId());

    return ResponseEntity
        .created(uriComponents.toUri())
        .build();


  }

  @DeleteMapping("/{accountId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void close(@PathVariable("userId") Long userId,
      @PathVariable("accountId") Long accountId) {
    service.closeAccount(userId, accountId);
  }


  @PutMapping("/{accountId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(@PathVariable("userId") Long userId,
      @PathVariable("accountId") Long accountId,
      @RequestBody @Validated NewAccountDTO dto) {

    service.updateUserAccount(userId, accountId, mapper.map(dto, Account.class));
  }

  @GetMapping("/{accountId}")
  public AccountDTO update(@PathVariable("userId") Long userId,
      @PathVariable("accountId") Long accountId) {

    return mapper.map(service.findAccountForUser(userId, accountId), AccountDTO.class);
  }


}
