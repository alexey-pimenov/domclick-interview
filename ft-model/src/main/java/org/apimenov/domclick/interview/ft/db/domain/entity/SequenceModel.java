package org.apimenov.domclick.interview.ft.db.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SequenceModel extends AbstractModel<Long> {


  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
  private Long id;

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
