package com.database.courses.repository;

import com.database.courses.entity.Course;
import com.database.courses.entity.Review;
import com.database.courses.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CourseSpringDataRepositoryTest {

    @Autowired
    CourseSpringDataRepository courseRepository;

    @Autowired
    StudentSpringDataRepository studentRepository;

    @Test
    @Transactional
    @DirtiesContext
    void findById() {
        //given
        long id = 10000L;

        //when
        Course course = courseRepository.findById(id).get();

        //then
        assertEquals("German", course.getName());
        assertEquals(2, course.getReviews().size());
        assertEquals(2, course.getStudents().size());
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
        Course course = courseRepository.findById(id).get();
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
    @Transactional
    @DirtiesContext
    void save_update_reviews() {
        //given
        long id = 10000L;
        String name = "German";
        Course course = courseRepository.findById(id).get();
        course.setName(name);
        course.addReview(new Review(3, "Something else to review..."));
        course.addReview(new Review(4, "Once again reviewing..."));

        //when
        Course saveCourse = courseRepository.save(course);

        //then
        assertEquals(id, saveCourse.getId());
        assertEquals(name, saveCourse.getName());
        assertEquals(4, saveCourse.getReviews().size());
    }

    @Test
    @Transactional
    @DirtiesContext
    void save_remove_reviews() {
        //given
        long id = 10000L;
        String name = "German";
        Course course = courseRepository.findById(id).get();
        course.setName(name);
        log.info("Reviews: {}", course.getReviews().size());
        List<Review> reviewList = course.getReviews().stream().toList();
        IntStream.range(0, course.getReviews().size()).forEach(value -> {
            var review = reviewList.get(value);
            log.info("Removing: {}", review.toString());
            course.removeReview(review);
        });

        //when
        Course saveCourse = courseRepository.save(course);

        //then
        assertEquals(id, saveCourse.getId());
        assertEquals(name, saveCourse.getName());
        assertEquals(0, saveCourse.getReviews().size());
    }


    @Test
    @Transactional
    @DirtiesContext
    void save_add_students() {
        //given
        long id = 10000L;
        String name = "German";
        Course course = courseRepository.findById(id).get();
        course.setName(name);
        Student newStudent = studentRepository.findById(20002L).get();
        course.addStudent(newStudent);

        //when
        Course saveCourse = courseRepository.save(course);

        //then
        assertEquals(id, saveCourse.getId());
        assertEquals(name, saveCourse.getName());
        assertEquals(3, saveCourse.getStudents().size());
    }

    @Test
    @Transactional
    @DirtiesContext
    void save_remove_students() {
        //given
        long id = 10000L;
        String name = "German";
        Course course = courseRepository.findById(id).get();
        course.setName(name);
        log.info("Reviews: {}", course.getReviews().size());
        List<Student> studentList = course.getStudents().stream().toList();
        IntStream.range(0, course.getStudents().size()).forEach(value -> {
            var student = studentList.get(value);
            log.info("Removing: {}", student.toString());
            course.removeStudent(student);
        });

        //when
        Course saveCourse = courseRepository.save(course);

        //then
        assertEquals(id, saveCourse.getId());
        assertEquals(name, saveCourse.getName());
        assertEquals(0, saveCourse.getStudents().size());
    }

    @Test
    @Transactional
    @DirtiesContext
    void deleteById() {
        //given
        long id = 10000L;

        //when
        assertEquals(3, studentRepository.findAll().size());
        courseRepository.deleteById(id);
        Optional<Course> courseSearch = courseRepository.findById(id);
        List<Review> courseById = courseRepository.getReviewsByCourse(id);

        //then
        assertFalse(courseSearch.isPresent());
        assertEquals(0, courseById.size());
        assertEquals(3, studentRepository.findAll().size());
        assertEquals(20000L, studentRepository.findById(20000L).get().getId());
        assertEquals(20001L, studentRepository.findById(20001L).get().getId());
        assertEquals(20002L, studentRepository.findById(20002L).get().getId());

        assertFalse(courseRepository.findById(10000L).isPresent());
        assertEquals(10001L, courseRepository.findById(10001L).get().getId());
        assertEquals(10002L, courseRepository.findById(10002L).get().getId());
        assertEquals(10003L, courseRepository.findById(10003L).get().getId());
    }

    @Test
    @Transactional
    @DirtiesContext
    void getReviews_from_entity_manager() {
        //given
        long id = 10000L;
        String name = "German";

        //when
        List<Review> coursesById = courseRepository.getReviewsByCourse(id);

        //then
        assertNotNull(coursesById);
        assertEquals(2, coursesById.size());
        coursesById.stream().forEach(review -> assertEquals(name, review.getCourse().getName()));
    }

    @Test
    void getCourseWithoutStudents() {
        //given

        //when
        List<Course> courseWithoutStudents = courseRepository.getCourseWithoutStudents();
        //then
        assertEquals(1, courseWithoutStudents.size());
        assertEquals(10003L, courseWithoutStudents.get(0).getId());
    }
}