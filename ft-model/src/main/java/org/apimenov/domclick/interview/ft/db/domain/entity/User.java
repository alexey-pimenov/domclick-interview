package org.apimenov.domclick.interview.ft.db.domain.entity;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@SequenceGenerator(name = "seq", sequenceName = "user_seq")
public class User extends SequenceModel {


  private String login;

  private String fullName;


  @OneToMany(mappedBy = "user", orphanRemoval = true)
  private Set<Account> accounts;


}
