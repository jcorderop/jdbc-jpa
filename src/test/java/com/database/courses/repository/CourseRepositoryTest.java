package com.database.courses.repository;

import com.database.courses.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseRepositoryTest {

    @Autowired
    CourseRepository courseRepository;

    @Test
    @DirtiesContext
    void findById() {
        //given
        long id = 10000L;

        //when
        Course course = courseRepository.findById(id);

        //then
        assertEquals("German", course.getName());
    }

    @Test
    @DirtiesContext
    void save() {
        //given
        String name = "Tarahumara";
        Course newCourse = new Course(name);

        //when
        Course course = courseRepository.save(newCourse);

        //then
        assertNotNull(course.getId());
        assertEquals(name, course.getName());
    }

    @Test
    void deleteById() {
        //given
        long id = 10000L;

        //when
        courseRepository.deleteById(id);
        Course courseSearch = courseRepository.findById(id);

        //then
        assertNull(courseSearch);
    }
}