package org.apimenov.domclick.interview.ft.db.repo;

import org.apimenov.domclick.interview.ft.db.domain.entity.SequenceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
interface SequenceModelRepository<T extends SequenceModel> extends JpaRepository<T, Long> {

}
