package com.database.courses.repository;

import com.database.courses.entity.Course;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
public class CacheTest {

    @Autowired
    CourseRepository repository;

    @Autowired
    EntityManager entityManager;

    @Test
    @Transactional
    @DirtiesContext
    void findById_first_level_cache() {
        //given
        long id = 10000L;

        //when
        log.info(">>>>> Getting 1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Course course = repository.findById(id);
        log.info(">>>>> Finished 1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info(">>>>> Getting 2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Course course2 = repository.findById(id);
        log.info(">>>>> Finished 2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        //then
        assertEquals("German", course.getName());
        //assertEquals(2, course.getReviews().size());
        //assertEquals(2, course.getStudents().size());
    }



    @Test
    @DirtiesContext
    void findById_second_level_cache() {
        //given
        long id = 10000L;

        //when
        log.info(">>>>> Getting 1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Course course = repository.findById(id);
        log.info(">>>>> Finished 1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info(">>>>> Getting 2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Course course2 = repository.findById(id);
        log.info(">>>>> Finished 2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        //then
        assertEquals("German", course.getName());
        //assertEquals(2, course.getReviews().size());
        //assertEquals(2, course.getStudents().size());
    }

}
