package org.apimenov.domclick.interview.ft.rest.domain.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserDTO {

  @NotNull
  @Size(min = 4, max = 255)
  private String fullName;

  @NotNull
  @Size(min = 4, max = 255)
  private String login;
}
