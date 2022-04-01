package com.database.courses.repository;

import com.database.courses.entity.Course;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
class NPlusOneProblemCourseRepositoryTest {

    @Autowired
    CourseRepository repository;

    @Autowired
    EntityManager entityManager;

    @Test
    @Transactional
    @DirtiesContext
    void findById() {
        //given
        long id = 10002L;

        //when
        final List<Course> result = entityManager.createNamedQuery("all_courses", Course.class)
                .setMaxResults(10)
                .getResultList();

        //then
        assertEquals(4, result.size());
        result.forEach(course -> course.getStudents().forEach(System.out::println));
    }

    @Test
    @Transactional
    @DirtiesContext
    void findById_graph() {
        //given
        long id = 10002L;
        EntityGraph<Course> entityGraph = entityManager.createEntityGraph(Course.class);
        entityGraph.addSubgraph("students");

        //when
        final List<Course> result = entityManager.createNamedQuery("all_courses", Course.class)
                .setHint("javax.persistence.loadgraph", entityGraph)
                .getResultList();

        //then
        assertEquals(4, result.size());
        result.forEach(course -> course.getStudents().forEach(System.out::println));
    }

    @Test
    @Transactional
    @DirtiesContext
    void findById_join_fetch() {
        //given
        long id = 10002L;

        //when
        final List<Course> result = entityManager.createNamedQuery("all_courses_join_fetch", Course.class)
                .getResultList();

        //then
        assertEquals(5, result.size());
        result.forEach(course -> course.getStudents().forEach(System.out::println));
    }

}