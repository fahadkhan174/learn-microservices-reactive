package com.fmg.userservice.config;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.util.StreamUtils;

@Configuration
public class Initializer implements CommandLineRunner {

  @Value("classpath:h2/init.sql")
  private Resource initSql;

  @Autowired
  private R2dbcEntityTemplate rEntityTemplate;

  @Override
  public void run(String... args) throws Exception {
    String query = StreamUtils.copyToString(initSql.getInputStream(), StandardCharsets.UTF_8);
    System.out.println(query);
    this.rEntityTemplate
        .getDatabaseClient()
        .sql(query)
        .then()
        .subscribe();
  }

}
