package com.database.courses.repository;

import com.database.courses.entity.Course;
import com.database.courses.entity.Passport;
import com.database.courses.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class StudentRepositoryTest {

    @Autowired
    StudentRepository repository;

    @Autowired
    EntityManager entityManager;

    @Test
    @Transactional
    @DirtiesContext
    void findById() {
        //given
        long id = 20000L;

        //when
        Student student = repository.findById(id);

        //then
        assertEquals("Jorge", student.getName());
        assertEquals(2, student.getCourses().size());
    }

    @Test
    @DirtiesContext
    void insert_user_with_passport() {
        //given
        String name = "Pepe";
        String passportNumber = "T10145";
        Student newStudent = new Student(name, new Passport(passportNumber, LocalDate.now()));

        //when
        List<Student> studentSaved = repository.insertWithPassport(newStudent);

        //then
        assertTrue(studentSaved.size() == 1);
        assertEquals(name, studentSaved.get(0).getName());
        assertEquals(passportNumber, studentSaved.get(0).getPassport().getNumber());
    }

    @Test
    @DirtiesContext
    void save_duplicate() {
        //given
        String name = "Jorge";
        Student newStudent = new Student();
        newStudent.setName(name);

        //when

        //then
        assertThrows(DataIntegrityViolationException.class, () -> repository.save(newStudent));
    }

    @Test
    @DirtiesContext
    void save_update_custom_fields() {
        //given
        long id = 20000L;
        String name = "Jorge Cordero";
        Student student = repository.findById(id);
        student.setName(name);
        //custom fields
        int version = student.getVersion() + 1;
        LocalDateTime createDate = student.getCreateDate();
        LocalDateTime updateDate = student.getUpdateDate();

        //when
        Student saveCourse = repository.save(student);

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
    //@Transactional
    void deleteById() {
        //given
        long id = 20000L;
        Object singleResultBefore = entityManager.createNativeQuery("select count(*) from student_course").getSingleResult();

        //when
        repository.deleteById(id);
        Student studentSearch = repository.findById(id);
        List singleResultAfter = getListOfCourses();

        //then
        //assertNull(studentSearch);
        assertEquals("5", singleResultBefore.toString());
        assertEquals(3, singleResultAfter.size());

        assertEquals(10000L, entityManager.find(Course.class, 10000L).getId());
        assertEquals(10001L, entityManager.find(Course.class, 10001L).getId());
        assertEquals(10002L, entityManager.find(Course.class, 10002L).getId());
        assertEquals(10003L, entityManager.find(Course.class, 10003L).getId());
    }

    private List getListOfCourses() {
        List singleResultAfter = entityManager.createNativeQuery("select student_id, course_id from student_course").getResultList();
        singleResultAfter.stream().forEach(o -> log.info("Courses {} - {}",singleResultAfter.get(0), singleResultAfter.get(1)));
        return singleResultAfter;
    }

    @Test
    @Transactional
    @DirtiesContext
    void save_add_course() {
        //given
        long courseId = 10003L;
        long studentId = 20000L;
        Course course = entityManager.find(Course.class, courseId);
        Student student = entityManager.find(Student.class, studentId);
        student.addCourse(course);

        //when
        Student saveStudent = repository.save(student);
        List singleResultAfter = getListOfCourses();

        //then
        assertEquals(studentId, saveStudent.getId());
        assertEquals(3, saveStudent.getCourses().size());
    }

    @Test
    @Transactional
    @DirtiesContext
    void save_remove_course() {
        //given
        long courseId = 10000L;
        long studentId = 20000L;
        Course course = entityManager.find(Course.class, courseId);
        Student student = entityManager.find(Student.class, studentId);

        student.removeCourse(course);

        //when
        Student saveStudent = repository.save(student);
        List singleResultAfter = getListOfCourses();

        //then
        assertEquals(studentId, saveStudent.getId());
        assertEquals(1, saveStudent.getCourses().size());
        assertEquals(courseId, entityManager.find(Course.class, courseId).getId());
    }

    @Test
    @Transactional
    @DirtiesContext
    void save_remove_all_course() {
        //given
        long courseId = 10000L;
        long studentId = 20000L;
        Course course = entityManager.find(Course.class, courseId);
        Student student = entityManager.find(Student.class, studentId);

        student.removeCourse(course);

        //when
        Student saveStudent = repository.save(student);

        //then
        assertEquals(studentId, saveStudent.getId());
        assertEquals(1, saveStudent.getCourses().size());
        assertEquals(courseId, entityManager.find(Course.class, courseId).getId());
    }
}