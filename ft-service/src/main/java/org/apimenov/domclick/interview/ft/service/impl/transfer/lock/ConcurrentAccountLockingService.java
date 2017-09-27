package org.apimenov.domclick.interview.ft.service.impl.transfer.lock;

import com.google.common.util.concurrent.Striped;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;
import org.apimenov.domclick.interview.ft.db.domain.entity.Account;
import org.apimenov.domclick.interview.ft.db.domain.entity.SequenceModel;
import org.apimenov.domclick.interview.ft.service.AccountLockingService;

public class ConcurrentAccountLockingService implements AccountLockingService {


  private Striped<Lock> locks = Striped.lazyWeakLock(10);

  @Override
  public void lock(List<Account> accounts) {

    List<Long> ids = accounts.stream()
        .map(SequenceModel::getId).sorted()
        .collect(Collectors.toList());

    ids.stream()
        .map(id -> locks.get(id))
        .forEachOrdered(Lock::lock);

  }

  @Override
  public void release(List<Account> accounts) {
    List<Long> ids = accounts.stream()
        .map(SequenceModel::getId).sorted()
        .collect(Collectors.toList());
    ids.stream()
        .map(id -> locks.get(id))
        .forEachOrdered(Lock::unlock);

  }
}
