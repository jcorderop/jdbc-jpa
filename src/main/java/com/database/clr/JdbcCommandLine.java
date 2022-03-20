package com.database.clr;

import com.database.entity.Person;
import com.database.jdbc.PersonJdbcDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
public class JdbcCommandLine implements CommandLineRunner {

    @Autowired
    private PersonJdbcDao personJdbcDao;

    @Override
    public void run(String... args) throws Exception {
        personJdbcDao.findAll().stream().forEach(person -> log.info(person.toString()));

        int id = 10001;
        log.info("Find by Id: {} - {}", id, personJdbcDao.findById(id).toString());

        log.info("Delete by Id: {}, num of rows deleted: {}", id, personJdbcDao.deleteById(id));

        log.info("Insert new Person: {}", personJdbcDao.insert(new Person(id, "Pepe", "Chihuahua", LocalDate.now())));

        log.info("Update new Person: {}", personJdbcDao.update(new Person(id, "Jose", "Casas Grandes", LocalDate.now())));

        personJdbcDao.findAll().stream().forEach(person -> log.info(person.toString()));
    }
}
