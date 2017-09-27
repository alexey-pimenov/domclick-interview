package org.apimenov.domclick.interview.ft.service.impl;

import java.util.List;
import org.apimenov.domclick.interview.ft.db.domain.entity.User;
import org.apimenov.domclick.interview.ft.db.repo.UserRepository;
import org.apimenov.domclick.interview.ft.service.UserService;
import org.apimenov.domclick.interview.ft.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository repository;

  @Autowired
  public UserServiceImpl(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<User> findAll() {
    return repository.findAll();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public User createUser(User user) {
    return repository.save(user);
  }


  @Override
  public User findById(Long userId) {
    return repository.findOne(userId);
  }


  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void update(User user) {

    if (repository.exists(user.getId())) {
      repository.save(user);
    } else {
      throw new EntityNotFoundException("User Not Found");
    }

  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void remove(Long userId) {

    try {
      repository.delete(userId);
    } catch (EmptyResultDataAccessException e) {
      throw new EntityNotFoundException("User not found");
    }
  }

  @Override
  public boolean exists(Long userId) {
    return repository.exists(userId);
  }
}
