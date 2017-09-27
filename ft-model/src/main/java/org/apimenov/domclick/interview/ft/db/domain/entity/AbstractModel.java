package org.apimenov.domclick.interview.ft.db.domain.entity;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;
import org.springframework.data.domain.Persistable;


@MappedSuperclass
public abstract class AbstractModel<ID extends Serializable> implements Persistable<ID> {


  @Override
  public boolean isNew() {
    return getId() == null;
  }
}
