package org.apimenov.domclick.interview.ft.service;

import java.util.List;
import org.apimenov.domclick.interview.ft.db.domain.entity.Account;

public interface UserAccountService {


  List<Account> findAllUserAccounts(Long userId);

  Account createAccountForUser(Long userId, Account account);

  void updateUserAccount(Long userId, Long accountId, Account account);

  void closeAccount(Long userId, Long accountId);

  Account findOne(Long accountId);

  Account findAccountForUser(Long userId, Long accountId);

  boolean isUserAccountExists(Long userId, Long accountId);


}
