package com.database.courses.repository;

import com.database.courses.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class NativeQueryTest {

    @Autowired
    EntityManager entityManager;

    //jpql
    @Test
    void findAll() {
        //given

        //when
        final List<Course> result = entityManager.createNativeQuery("select * from Course c", Course.class)
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
        final List<Course> result = entityManager.createNativeQuery("select * from Course c where c.name like ?", Course.class)
                .setParameter(1, name)
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
        final List<Course> result = entityManager.createNativeQuery("select c.id, c.name, c.version, c.create_date, c.update_date, c.deleted from Course c where c.name = ?", Course.class)
                .setParameter(1, name)
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
        final List<Course> result = entityManager.createNativeQuery("select id, name, version, create_date, update_date, deleted from Course c where c.id = ? ", Course.class)
                .setParameter(1, id)
                .getResultList();

        //then
        assertEquals(1, result.size());
        assertEquals(10000L, result.get(0).getId());
        assertEquals("German", result.get(0).getName());
    }

    @Test
    @DirtiesContext
    @Transactional
    void mass_update() {
        //given
        LocalDateTime new_update_date = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

        //when
        final Query query = entityManager.createNativeQuery("update Course set update_date = ? ", Course.class)
                .setParameter(1, new_update_date);
        query.executeUpdate();

        final List<Course> result = entityManager.createNativeQuery("select * from Course c", Course.class)
                .setMaxResults(10)
                .getResultList();

        //then
        assertTrue(result.size() > 0);
        result.stream().forEach(course -> {
            System.out.printf(course.toString());
            assertEquals(new_update_date, course.getUpdateDate());
        });
    }
}
