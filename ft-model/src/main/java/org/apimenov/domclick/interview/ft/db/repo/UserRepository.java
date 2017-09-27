package org.apimenov.domclick.interview.ft.db.repo;

import org.apimenov.domclick.interview.ft.db.domain.entity.User;

public interface UserRepository extends SequenceModelRepository<User> {


  User findUserByLogin(String login);
}
