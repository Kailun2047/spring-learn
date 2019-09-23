package com.example.accessdatajpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccessdatajpaApplication {
    private Logger logger = LoggerFactory.getLogger(AccessdatajpaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AccessdatajpaApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(CustomerRepository repo) {
        return (args) -> {
            repo.save(new Customer("Kailun", "Li"));
            repo.save(new Customer("Kyle", "Li"));
            repo.save(new Customer("Michael", "Du"));

            logger.info("Customer found with findAll():");
            for (Customer c: repo.findAll()) {
                logger.info(c.toString());
            }
            logger.info("");

            logger.info("Customer found wth findByLastName():");
            repo.findByLastName("Li").forEach(li -> {
                logger.info(li.toString());
            });
            logger.info("");
        };
    }
}
