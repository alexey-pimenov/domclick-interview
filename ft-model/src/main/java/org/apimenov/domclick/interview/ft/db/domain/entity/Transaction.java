package org.apimenov.domclick.interview.ft.db.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.apimenov.domclick.interview.ft.db.domain.TransactionStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@Table(name = "transaction")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "seq", sequenceName = "transaction_seq")
public class Transaction extends SequenceModel {

  @ManyToOne
  @JoinColumn(name = "id_account")
  private Account account;


  @Enumerated(EnumType.STRING)
  private TransactionStatus status;


  private BigDecimal amount;

  @Column(name = "time_created")
  @CreatedDate
  private LocalDateTime timeCreated;
}
