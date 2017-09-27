package org.apimenov.domclick.interview.ft.db.repo;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.apimenov.domclick.interview.ft.db.domain.entity.Account;
import org.apimenov.domclick.interview.ft.db.domain.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryTest extends RepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Test
  public void testAccountsOrphanRemoval(){
    List<User> accounts = userRepository.findAll();

    userRepository.delete(accounts);

    assertEquals(0L, accountRepository.count());
  }

}
