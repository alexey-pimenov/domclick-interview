package org.apimenov.domclick.interview.ft.db.domain.entity;


import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "account")
@Getter
@Setter
@SequenceGenerator(name = "seq", sequenceName = "account_seq")
public class Account extends SequenceModel {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private BigDecimal balance;

  @ManyToOne(optional = false)
  @JoinColumn(name = "id_user")
  private User user;

  @OneToMany(mappedBy = "account", orphanRemoval = true)
  private Set<Transaction> transactions;


}
