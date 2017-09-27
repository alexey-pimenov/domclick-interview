package org.apimenov.domclick.interview.ft.db.repo;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.LockModeType;
import org.apimenov.domclick.interview.ft.db.domain.entity.Account;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends SequenceModelRepository<Account> {


  List<Account> findAccountsByUserId(Long userId);

  @Modifying
  @Query("update Account acc set acc.balance = acc.balance+:amount where acc  = :account")
  void updateBalance(@Param("account") Account account, @Param("amount") BigDecimal balance);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select acc from Account acc where acc.id in :ids")
  List<Account> selectForUpdate(@Param("ids") List<Long> ids);

}
