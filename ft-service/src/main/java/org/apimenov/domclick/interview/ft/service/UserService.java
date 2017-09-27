package org.apimenov.domclick.interview.ft.service;

import java.util.List;
import org.apimenov.domclick.interview.ft.db.domain.entity.User;

public interface UserService {

  List<User> findAll();

  User createUser(User user);


  User findById(Long userId);


  void update(User user);

  void remove(Long userId);

  boolean exists(Long userId);

}
