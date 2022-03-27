package com.database.courses.repository;

import com.database.courses.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EntityFunctionsTest {

    @Autowired
    CourseRepository courseRepository;

    @Test
    @DirtiesContext
    void persistTest() {
        //given
        String name = "set something else";

        //when
        Course course = courseRepository.persistTest(name);

        //then
        assertNotNull(course.getId());
        assertEquals(name, course.getName());
    }

    @Test
    @DirtiesContext
    void detachTest() {
        //given
        String name = "set something else";

        //when
        Course course = courseRepository.detachTest(name);
        Course courseSearch = courseRepository.findById(course.getId());

        //then
        assertNotNull(course.getId());
        assertEquals(name, course.getName());
        assertEquals("Test", courseSearch.getName());
    }

    @Test
    @DirtiesContext
    void clearTest() {
        //given
        String name = "set something else";

        //when
        Course course = courseRepository.clearTest(name);
        Course courseSearch = courseRepository.findById(course.getId());

        //then
        assertNotNull(course.getId());
        assertEquals(name, course.getName());
        assertEquals("Test", courseSearch.getName());
    }

    @Test
    @DirtiesContext
    void refreshTest() {
        //given
        String name = "set something else";

        //when
        Course course = courseRepository.refreshTest(name);
        Course courseSearch = courseRepository.findById(course.getId());

        //then
        assertNotNull(course.getId());
        assertEquals("Test", course.getName());
        assertEquals("Test", courseSearch.getName());
    }
}