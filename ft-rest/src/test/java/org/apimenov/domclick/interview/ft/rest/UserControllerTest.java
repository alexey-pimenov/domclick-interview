package org.apimenov.domclick.interview.ft.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

public class UserControllerTest extends ControllerTest {


  @Test
  public void testGetAll() throws Exception {
    mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().json(getResource("UserController.getAll.json")));

  }

  @Test
  public void testGetOne() throws Exception {
    mvc.perform(get("/users/1").accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().json(getResource("UserController.getOne.json")));
  }

  @Test
  public void testGetOne404() throws Exception {
    mvc.perform(get("/users/666").accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isNotFound())
        .andExpect(content().json(getResource("404.json")));
  }

}
