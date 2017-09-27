package org.apimenov.domclick.interview.ft.db.repo;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.apimenov.domclick.interview.ft.db.domain.entity.Account;
import org.apimenov.domclick.interview.ft.db.domain.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class AccountRepositoryTest {

  @Autowired
  private AccountRepository repository;

  @Autowired
  private UserRepository userRepository;


  @Test
  public void testFindAccountByUserId() {
    User user = userRepository.findUserByLogin("petya");
    List<Account> accounts = repository.findAccountsByUserId(user.getId());

    assertEquals("There should be one account", 1, accounts.size());
    assertEquals("With name 'petya-main'", "petya-main", accounts.get(0).getName());


  }
}
