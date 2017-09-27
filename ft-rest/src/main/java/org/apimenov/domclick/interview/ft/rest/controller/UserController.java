package org.apimenov.domclick.interview.ft.rest.controller;


import java.util.List;
import java.util.stream.Collectors;
import org.apimenov.domclick.interview.ft.db.domain.entity.User;
import org.apimenov.domclick.interview.ft.rest.domain.dto.NewUserDTO;
import org.apimenov.domclick.interview.ft.rest.domain.dto.UserDTO;
import org.apimenov.domclick.interview.ft.service.UserService;
import org.apimenov.domclick.interview.ft.service.exception.EntityNotFoundException;
import org.apimenov.domclick.interview.ft.service.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/users")
public class UserController {


  private UserService service;
  private ModelMapper mapper;

  @Autowired
  public UserController(UserService service, ModelMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @GetMapping
  public List<UserDTO> getAll() {
    return service.findAll().stream()
        .map(e -> mapper.map(e, UserDTO.class))
        .collect(Collectors.toList());
  }


  @PostMapping
  public ResponseEntity<UserDTO> create(@Validated @RequestBody NewUserDTO user,
      UriComponentsBuilder b) {
    User entity = mapper.map(user, User.class);

    entity = service.createUser(entity);

    UriComponents uriComponents =
        b.path("/user/{id}").buildAndExpand(entity.getId());

    return ResponseEntity

        .created(uriComponents.toUri())
        .body(mapper.map(entity, UserDTO.class));
  }


  @PutMapping("/{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(@Validated @RequestBody NewUserDTO user, @PathVariable("userId") Long userId) {
    User entity = mapper.map(user, User.class);
    entity.setId(userId);
    service.update(entity);
  }


  @DeleteMapping(path = "/{userId}")
  public ResponseEntity<?> delete(@PathVariable("userId") Long id) throws EntityNotFoundException {
    service.remove(id);

    return ResponseEntity.noContent().build();
  }


  @GetMapping(path = "/{userId}")
  public ResponseEntity<UserDTO> getById(@PathVariable("userId") Long id) {
    User user = service.findById(id);
    if (user != null) {
      return ResponseEntity.ok(mapper.map(user, UserDTO.class));
    } else {
      throw new UserNotFoundException();
    }

  }
}
