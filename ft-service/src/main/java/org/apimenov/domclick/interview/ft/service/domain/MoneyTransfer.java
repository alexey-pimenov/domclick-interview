package org.apimenov.domclick.interview.ft.service.domain;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MoneyTransfer {


  private Long userId;

  private Long fromAccountId;

  private Long toAccountId;

  private BigDecimal amount;

  private OperationType type;


}
