package org.apimenov.domclick.interview.ft.service.impl.transfer.lock;

import java.util.List;
import java.util.stream.Collectors;
import org.apimenov.domclick.interview.ft.db.domain.entity.Account;
import org.apimenov.domclick.interview.ft.db.domain.entity.SequenceModel;
import org.apimenov.domclick.interview.ft.db.repo.AccountRepository;
import org.apimenov.domclick.interview.ft.service.AccountLockingService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class DBAccountLockingService implements AccountLockingService {


  private final AccountRepository repository;

  public DBAccountLockingService(AccountRepository repository) {
    this.repository = repository;
  }


  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void lock(List<Account> accounts) {
    List<Long> ids = accounts.stream()
        .map(SequenceModel::getId).sorted()
        .collect(Collectors.toList());
    repository.selectForUpdate(ids);

  }

  @Override
  public void release(List<Account> accounts) {

  }
}
