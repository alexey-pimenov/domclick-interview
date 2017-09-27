package org.apimenov.domclick.interview.ft.rest.domain.dto;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewAccountDTO {


  @Size(min = 4, max = 255)
  private String name;

  @Min(0)
  @NotNull
  private BigDecimal balance;

}
