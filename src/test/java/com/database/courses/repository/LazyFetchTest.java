package com.database.courses.repository;

import com.database.courses.entity.Passport;
import com.database.courses.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class LazyFetchTest {
    @Autowired
    EntityManager entityManager;

    //jpql
    @Test
    @Transactional
    void get_student_then_passport() {
        //given
        long id = 20000L;
        String name = "Jorge";
        String passportNumber = "J1002";

        //when
        Student student = entityManager.find(Student.class, id);

        //then
        assertEquals(id, student.getId());
        assertEquals(name, student.getName());
        assertEquals(passportNumber, student.getPassport().getNumber());
    }

    @Test
    @Transactional
    void get_passport_then_student() {
        //given
        long id = 30000L;
        String name = "Jorge";
        String passportNumber = "J1002";

        //when
        Passport passport = entityManager.find(Passport.class, id);

        //then
        assertEquals(id, passport.getId());
        assertEquals(passportNumber, passport.getNumber());
        assertEquals(name, passport.getStudent().getName());
    }
}
