package org.apimenov.domclick.interview.ft.service.impl.transfer.lock;

import com.google.common.util.concurrent.Striped;
import java.util.List;
import java.util.concurrent.locks.Lock;
import org.apimenov.domclick.interview.ft.db.domain.entity.Account;
import org.apimenov.domclick.interview.ft.db.domain.entity.SequenceModel;
import org.apimenov.domclick.interview.ft.service.AccountLockingService;

public class ConcurrentAccountLockingService implements AccountLockingService {


  private Striped<Lock> locks = Striped.lazyWeakLock(10);

  @Override
  public void lock(List<Account> accounts) {
    accounts.stream()
        .map(SequenceModel::getId).sorted()
        .forEachOrdered(id -> locks.get(id).lock());


  }

  @Override
  public void release(List<Account> accounts) {
    accounts.stream()
        .map(SequenceModel::getId).sorted()
        .forEachOrdered(id -> locks.get(id).unlock());

  }
}
