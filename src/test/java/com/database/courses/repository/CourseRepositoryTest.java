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

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CourseRepositoryTest {

    @Autowired
    CourseRepository repository;

    @Autowired
    EntityManager entityManager;

    @Test
    @Transactional
    @DirtiesContext
    void findById() {
        //given
        long id = 10000L;

        //when
        Course course = repository.findById(id);

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
        Course course = repository.save(newCourse);

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
        assertThrows(DataIntegrityViolationException.class, () -> repository.save(newCourse));
    }

    @Test
    @DirtiesContext
    void save_update_custom_fields() {
        //given
        long id = 10000L;
        String name = "German B2";
        Course course = repository.findById(id);
        course.setName(name);
        //custom fields
        int version = course.getVersion() + 1;
        LocalDateTime createDate = course.getCreateDate();
        LocalDateTime updateDate = course.getUpdateDate();

        //when
        Course saveCourse = repository.save(course);

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
        Course course = repository.findById(id);
        course.setName(name);
        course.addReview(new Review(3, "Something else to review..."));
        course.addReview(new Review(4, "Once again reviewing..."));

        //when
        Course saveCourse = repository.save(course);

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
        Course course = repository.findById(id);
        course.setName(name);
        log.info("Reviews: {}", course.getReviews().size());
        List<Review> reviewList = course.getReviews().stream().toList();
        IntStream.range(0, course.getReviews().size()).forEach(value -> {
            var review = reviewList.get(value);
            log.info("Removing: {}", review.toString());
            course.removeReview(review);
        });

        //when
        Course saveCourse = repository.save(course);

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
        Course course = repository.findById(id);
        course.setName(name);
        Student newStudent = entityManager.find(Student.class, 20002L);
        course.addStudent(newStudent);

        //when
        Course saveCourse = repository.save(course);

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
        Course course = repository.findById(id);
        course.setName(name);
        log.info("Reviews: {}", course.getReviews().size());
        List<Student> studentList = course.getStudents().stream().toList();
        IntStream.range(0, course.getStudents().size()).forEach(value -> {
            var student = studentList.get(value);
            log.info("Removing: {}", student.toString());
            course.removeStudent(student);
        });

        //when
        Course saveCourse = repository.save(course);

        //then
        assertEquals(id, saveCourse.getId());
        assertEquals(name, saveCourse.getName());
        assertEquals(0, saveCourse.getStudents().size());
    }

    @Test
    @DirtiesContext
    void deleteById() {
        //given
        long id = 10000L;

        //when
        repository.deleteById(id);
        Course courseSearch = repository.findById(id);
        List<Review> courseById = entityManager.createQuery("select r from Review r where r.course.id = :course_id")
                .setParameter("course_id", id)
                .getResultList();

        //then
        assertNull(courseSearch);
        assertEquals(0, courseById.size());
        assertEquals(20000L, entityManager.find(Student.class, 20000L).getId());
        assertEquals(20001L, entityManager.find(Student.class, 20001L).getId());
        assertEquals(20002L, entityManager.find(Student.class, 20002L).getId());
    }

    @Test
    @Transactional
    @DirtiesContext
    void getReviews_from_entity_manager() {
        //given
        long id = 10000L;
        String name = "German";

        //when
        List<Review> coursesById = entityManager.createQuery("select r from Review r where r.course.id = :course_id").setParameter("course_id", id).getResultList();

        //then
        assertNotNull(coursesById);
        assertEquals(2, coursesById.size());
        coursesById.stream().forEach(review -> assertEquals(name, review.getCourse().getName()));
    }

    @Test
    void getCourseWithoutStudents() {
        //given

        //when
        List<Course> courseWithoutStudents = repository.getCourseWithoutStudents();
        //then
        assertEquals(1, courseWithoutStudents.size());
        assertEquals(10003L, courseWithoutStudents.get(0).getId());
    }
}