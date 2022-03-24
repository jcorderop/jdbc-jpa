package com.database.jdbc_jpa.clr;

import com.database.jdbc_jpa.entity.PersonEntity;
import com.database.jdbc_jpa.jpa.PersonJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
public class JpaCommandLine implements CommandLineRunner {

    @Autowired
    private PersonJpaRepository repository;

    @Override
    public void run(String... args) {
        //repository.findAll().stream().forEach(person -> log.info(person.toString()));

        int id = 1;

        PersonEntity insert = repository.insert(new PersonEntity("Pepe", "Chihuahua", LocalDate.now()));
        log.info("Insert new Person: {}", insert);

        repository.findAll().forEach(person -> log.info(">>> "+person.toString()));

        final PersonEntity update = repository.update(new PersonEntity(id,"Jose", "Casas Grandes", LocalDate.now()));
        log.info("Update new Person: {}", update);

        log.info("Find by Id: {} - {}", id, repository.findById(id));

        repository.deleteById(id);

        log.info("Find by Id: {} - {}", id, repository.findById(id));

        repository.findAll().forEach(person -> log.info(person.toString()));
    }
}
