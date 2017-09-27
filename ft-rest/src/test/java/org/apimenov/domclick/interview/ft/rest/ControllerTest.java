package org.apimenov.domclick.interview.ft.rest;

import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.FileCopyUtils;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class ControllerTest {

  @Autowired
  protected MockMvc mvc;


  protected String getResource(String path) {
    try {
      ClassPathResource resource = new ClassPathResource(path, this.getClass());

      return FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
