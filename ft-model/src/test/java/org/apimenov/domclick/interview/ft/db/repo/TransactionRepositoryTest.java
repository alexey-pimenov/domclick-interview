package org.apimenov.domclick.interview.ft.db.repo;


import java.math.BigDecimal;
import java.util.List;
import org.apimenov.domclick.interview.ft.db.domain.entity.Account;
import org.apimenov.domclick.interview.ft.db.domain.entity.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionRepositoryTest extends RepositoryTest {


  @Autowired
  private TransactionRepository repository;

  @Autowired
  private AccountRepository accountRepository;


  @Test
  public void testFindByUserAndAccountId(){
    List<Transaction> transactions = repository.findAllByUserAndAccountId(3L,3L);

    Assert.assertEquals(4,transactions.size());

  }


  @Test
  public void testAudit(){
    Account account = accountRepository.getOne(3L);
    Transaction transaction = new Transaction();

    transaction.setAmount(new BigDecimal(1L));
    transaction.setAccount(account);

    transaction = repository.save(transaction);

    Assert.assertNotNull(transaction.getTimeCreated());
  }

}
