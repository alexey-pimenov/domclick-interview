package org.apimenov.domclick.interview.ft.rest.domain.dto;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.apimenov.domclick.interview.ft.rest.validator.MoneyTransferConstraint;
import org.apimenov.domclick.interview.ft.service.domain.OperationType;

@Getter
@Setter
@MoneyTransferConstraint
public class TransferDTO {

  private OperationType type;

  @NotNull
  @Min(0)
  private BigDecimal amount;

  private Long toAccountId;


}
