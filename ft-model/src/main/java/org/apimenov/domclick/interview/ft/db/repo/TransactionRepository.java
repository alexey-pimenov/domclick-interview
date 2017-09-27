package org.apimenov.domclick.interview.ft.db.repo;

import java.util.List;
import org.apimenov.domclick.interview.ft.db.domain.entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends SequenceModelRepository<Transaction> {
  //Just because JPA doesn't support unions

  @Query("select tr from Transaction tr where  tr.account.id = :accountId and tr.account.user.id = :userId")
  List<Transaction> findAllByUserAndAccountId(@Param("userId") Long userId,
      @Param("accountId") Long accountId);


}
