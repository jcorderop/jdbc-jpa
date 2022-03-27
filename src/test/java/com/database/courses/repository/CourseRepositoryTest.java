package com.database.courses.repository;

import com.database.courses.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;

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
    @DirtiesContext
    void save_duplicate() {
        //given
        String name = "German";
        Course newCourse = new Course(name);

        //when

        //then
        assertThrows(DataIntegrityViolationException.class, () -> courseRepository.save(newCourse));
    }

    @Test
    @DirtiesContext
    void save_update_custom_fields() {
        //given
        long id = 10000L;
        String name = "German B2";
        Course course = courseRepository.findById(id);
        course.setName(name);
        //custom fields
        int version = course.getVersion() + 1;
        LocalDateTime createDate = course.getCreateDate();
        LocalDateTime updateDate = course.getUpdateDate();

        //when
        Course saveCourse = courseRepository.save(course);

        //then
        assertEquals(id, saveCourse.getId());
        assertEquals(name, saveCourse.getName());
        //custom fields
        assertEquals(version, saveCourse.getVersion());
        assertEquals(createDate, saveCourse.getCreateDate());
        assertTrue(saveCourse.getUpdateDate().isAfter(updateDate));
    }

    @Test
    @DirtiesContext
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