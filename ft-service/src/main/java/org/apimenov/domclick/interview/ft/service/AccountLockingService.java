package org.apimenov.domclick.interview.ft.service;

import java.util.List;
import org.apimenov.domclick.interview.ft.db.domain.entity.Account;

public interface AccountLockingService {


  void lock(List<Account> accounts);

  void release(List<Account> account);


}
