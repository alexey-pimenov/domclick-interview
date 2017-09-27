package org.apimenov.domclick.interview.ft.service.config;

import java.util.concurrent.Executor;
import org.apimenov.domclick.interview.ft.db.config.DatabaseConfiguration;
import org.apimenov.domclick.interview.ft.db.repo.AccountRepository;
import org.apimenov.domclick.interview.ft.db.repo.TransactionRepository;
import org.apimenov.domclick.interview.ft.service.AccountLockingService;
import org.apimenov.domclick.interview.ft.service.MoneyTransferService;
import org.apimenov.domclick.interview.ft.service.impl.transfer.AsyncMoneyTransferService;
import org.apimenov.domclick.interview.ft.service.impl.transfer.SimpleMoneyTransferService;
import org.apimenov.domclick.interview.ft.service.impl.transfer.UserTransactionExecutor;
import org.apimenov.domclick.interview.ft.service.impl.transfer.lock.ConcurrentAccountLockingService;
import org.apimenov.domclick.interview.ft.service.impl.transfer.lock.DBAccountLockingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync(proxyTargetClass = true)
@ComponentScan(basePackages = "org.apimenov.domclick.interview.ft.service")
public class FtServiceConfiguration extends DatabaseConfiguration {


  @Profile("db-locking")
  @Bean
  public AccountLockingService accountLockingService(AccountRepository repository) {
    return new DBAccountLockingService(repository);
  }

  @Profile("reentrant-locking")
  @Bean
  public AccountLockingService reentrantLockingService() {
    return new ConcurrentAccountLockingService();
  }


  @Profile("simple")
  @Bean
  public MoneyTransferService moneyTransferService(
      TransactionRepository transactionRepository,
      AccountRepository accountRepository) {
    return new SimpleMoneyTransferService(transactionRepository, accountRepository);
  }

  @Profile("async-user-transactions")
  @Bean
  public MoneyTransferService moneyTransferService(
      TransactionRepository transactionRepository,
      AccountRepository accountRepository,
      UserTransactionExecutor executor) {
    return new AsyncMoneyTransferService(transactionRepository, accountRepository, executor);
  }

  @Profile("async-user-transactions")
  @Bean
  public Executor asyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
    executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 4);
    executor.setQueueCapacity(10000);
    executor.setThreadNamePrefix("User-Transactions-");
    executor.initialize();
    return executor;
  }


}
