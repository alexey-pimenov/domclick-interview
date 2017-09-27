package org.apimenov.domclick.interview.ft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(FtRestConfiguration.class)
@SpringBootApplication
public class FtApplication {


  public static void main(String... args) {
    SpringApplication.run(FtApplication.class, args);
  }
}
