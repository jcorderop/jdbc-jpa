package com.database.courses.repository;

import com.database.courses.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JPQLTest {

    @Autowired
    EntityManager entityManager;

    //jpql
    @Test
    void findAll() {
        //given

        //when
        final List<Course> result = entityManager.createQuery("select c from Course c")
                .setMaxResults(10)
                .getResultList();

        //then
        assertTrue(result.size() > 0);
    }

    @Test
    void findAllTyped() {
        //given

        //when
        final List<Course> result = entityManager.createNamedQuery("all_courses", Course.class)
                .setMaxResults(10)
                .getResultList();

        //then
        assertTrue(result.size() > 0);
    }

    @Test
    void findAllByNameLike() {
        //given
        String name = "Ger%";

        //when
        final List<Course> result = entityManager.createQuery("select c from Course c where c.name like :name", Course.class)
                .setParameter("name", name)
                .setMaxResults(10)
                .getResultList();

        //then
        assertEquals(1, result.size());
        assertEquals(10000L, result.get(0).getId());
        assertEquals("German", result.get(0).getName());
    }

    @Test
    void findByName() {
        //given
        String name = "German";

        //when
        final List<Course> result = entityManager.createQuery("select c from Course c where c.name = :name", Course.class)
                .setParameter("name", name)
                .getResultList();

        //then
        assertEquals(1, result.size());
        assertEquals(10000L, result.get(0).getId());
        assertEquals("German", result.get(0).getName());
    }

    @Test
    void findById() {
        //given
        Long id = 10000L;

        //when
        final List<Course> result = entityManager.createQuery("select c from Course c where c.id = :id ", Course.class)
                .setParameter("id", id)
                .getResultList();

        //then
        assertEquals(1, result.size());
        assertEquals(10000L, result.get(0).getId());
        assertEquals("German", result.get(0).getName());
    }
}
