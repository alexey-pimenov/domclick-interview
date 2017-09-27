package org.apimenov.domclick.interview.ft.service.impl;

import java.util.List;
import org.apimenov.domclick.interview.ft.db.domain.entity.Account;
import org.apimenov.domclick.interview.ft.db.domain.entity.User;
import org.apimenov.domclick.interview.ft.db.repo.AccountRepository;
import org.apimenov.domclick.interview.ft.service.UserAccountService;
import org.apimenov.domclick.interview.ft.service.UserService;
import org.apimenov.domclick.interview.ft.service.exception.AccountNotFoundException;
import org.apimenov.domclick.interview.ft.service.exception.EntityNotFoundException;
import org.apimenov.domclick.interview.ft.service.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserAccountServiceImpl implements UserAccountService {


  private AccountRepository repository;

  private UserService userService;


  public UserAccountServiceImpl(
      AccountRepository repository,
      UserService userService) {
    this.repository = repository;
    this.userService = userService;
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS)
  public List<Account> findAllUserAccounts(Long userId) {
    checkIfUserExists(userId);
    return repository.findAccountsByUserId(userId);

  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Account createAccountForUser(Long userId, Account account) {
    User user = userService.findById(userId);

    if (user == null) {
      throwUserNotFound(userId);
    }

    account.setUser(user);

    return repository.save(account);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void updateUserAccount(Long userId, Long accountId, Account account) {

    Account original = findOne(accountId);

    if (original == null) {
      throw new AccountNotFoundException("Account ID:" + accountId + " not found");
    }

    original.setName(account.getName());
    original.setBalance(account.getBalance());

    repository.save(original);

  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void closeAccount(Long userId, Long accountId) {
    Account acc = findAccountForUser(userId, accountId);

    repository.delete(acc);


  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS)
  public Account findOne(Long accountId) {
    Account original = repository.findOne(accountId);

    if (original == null) {
      throw new EntityNotFoundException("Account ID:" + accountId + " not found");
    }

    return original;
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public Account findAccountForUser(Long userId, Long accountId) {
    Account account = findOne(accountId);

    if (!account.getUser().getId().equals(userId)) {
      throw new AccountNotFoundException(
          "Account ID:" + accountId + " not found for user ID: " + userId);
    }
    return account;
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS)
  public boolean isUserAccountExists(Long userId, Long accountId) {
    return userService.exists(userId) && repository.exists(accountId);
  }


  private void checkIfUserExists(Long userId) {
    if (!userService.exists(userId)) {
      throwUserNotFound(userId);
    }
  }

  private void throwUserNotFound(Long userId) {
    throw new UserNotFoundException("User ID:" + userId + " not found");
  }
}
