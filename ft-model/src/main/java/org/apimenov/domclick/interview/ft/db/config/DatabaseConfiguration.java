package org.apimenov.domclick.interview.ft.db.config;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/***
 * Dummy configuration for spring boot to work
 */
@PropertySource("classpath:database.properties")
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "org.apimenov.domclick.interview.ft.db.repo")
@EntityScan(basePackages = "org.apimenov.domclick.interview.ft.db.domain.entity")
public class DatabaseConfiguration {

}
