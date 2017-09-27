package org.apimenov.domclick.interview.ft.rest.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.apimenov.domclick.interview.ft.db.domain.TransactionStatus;

@Getter
@Setter
public class TransactionDTO {

  private Long id;

  private BigDecimal amount;

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private LocalDateTime timeCreated;

  private TransactionStatus status;

}
