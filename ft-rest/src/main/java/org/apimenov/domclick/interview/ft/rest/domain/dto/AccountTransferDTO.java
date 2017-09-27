package org.apimenov.domclick.interview.ft.rest.domain.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountTransferDTO extends TransferDTO {

  @NotNull
  private Long toAccountId;
}
