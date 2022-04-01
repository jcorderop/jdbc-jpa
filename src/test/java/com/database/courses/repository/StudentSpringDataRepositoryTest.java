package com.database.courses.repository;

import com.database.courses.entity.Address;
import com.database.courses.entity.Course;
import com.database.courses.entity.Passport;
import com.database.courses.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class StudentSpringDataRepositoryTest {

    @Autowired
    StudentSpringDataRepository studentRepository;

    @Autowired
    CourseSpringDataRepository courseRepository;

    @Test
    @Transactional
    @DirtiesContext
    void findById() {
        //given
        long id = 20000L;

        //when
        Student student = studentRepository.findById(id).get();

        //then
        assertEquals("Jorge", student.getName());
        assertEquals(2, student.getCourses().size());
    }

    @Test
    @Transactional
    @DirtiesContext
    void insert_user_with_passport() {
        //given
        String name = "Pepe";
        String passportNumber = "T10145";
        Student newStudent = new Student(name, new Passport(passportNumber, LocalDate.now()));

        //when
        Student studentSaved = studentRepository.save(newStudent);

        //then
        assertEquals(name, studentSaved.getName());
        assertEquals(passportNumber, studentSaved.getPassport().getNumber());
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
        assertThrows(DataIntegrityViolationException.class, () -> studentRepository.save(newStudent));
    }

    @Test
    @DirtiesContext
    void save_update_custom_fields() {
        //given
        long id = 20000L;
        String name = "Jorge Cordero";
        Student student = studentRepository.findById(id).get();
        student.setName(name);
        student.setAddress(new Address("Winterthur", "Kamillestrasse", "26", 8045));

        //when
        Student saveCourse = studentRepository.save(student);

        //then
        assertEquals(id, saveCourse.getId());
        assertEquals(name, saveCourse.getName());
        //custom fields
        assertEquals("Winterthur", saveCourse.getAddress().getCity());
        assertEquals("Kamillestrasse", saveCourse.getAddress().getStreet());
        assertEquals("26", saveCourse.getAddress().getNumber());
        assertEquals(8045, saveCourse.getAddress().getPostCode());
    }

    @Test
    @DirtiesContext
    void save_address() {
        //given
        long id = 20000L;
        String name = "Jorge Cordero";
        Student student = studentRepository.findById(id).get();
        student.setName(name);
        //custom fields
        int version = student.getVersion() + 1;
        LocalDateTime createDate = student.getCreateDate();
        LocalDateTime updateDate = student.getUpdateDate();

        //when
        Student saveCourse = studentRepository.save(student);

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
    @Transactional
    void deleteById() {
        //given
        long id = 20000L;
        List<Tuple> singleResultBefore = getListOfCourses();

        //when
        studentRepository.deleteById(id);
        Optional<Student> studentSearch = studentRepository.findById(id);


        //then
        assertFalse(studentSearch.isPresent());
        assertEquals(5, singleResultBefore.size());
        //List<Tuple> singleResultAfter = getListOfCourses();
        //assertEquals(3, singleResultAfter.size());

        assertEquals(10000L, courseRepository.findById(10000L).get().getId());
        assertEquals(10001L, courseRepository.findById(10001L).get().getId());
        assertEquals(10002L, courseRepository.findById(10002L).get().getId());
        assertEquals(10003L, courseRepository.findById(10003L).get().getId());

        assertFalse(studentRepository.findById(20000L).isPresent());
        assertEquals(20001L, studentRepository.findById(20001L).get().getId());
        assertEquals(20002L, studentRepository.findById(20002L).get().getId());
    }

    private List<Tuple> getListOfCourses() {
        List<Tuple> coursesWithStudents = courseRepository.getStudentsCourses();
        coursesWithStudents.forEach(course-> log.info("Course {}, Student{}", course.get(0), course.get(1)));
        return coursesWithStudents;
    }

    @Test
    @Transactional
    @DirtiesContext
    void save_add_course() {
        //given
        long courseId = 10003L;
        long studentId = 20000L;
        Course course = courseRepository.findById(courseId).get();
        Student student = studentRepository.getById(studentId);
        student.addCourse(course);

        //when
        Student saveStudent = studentRepository.save(student);
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
        Course course = courseRepository.findById(courseId).get();
        Student student = studentRepository.getById(studentId);

        student.removeCourse(course);

        //when
        Student saveStudent = studentRepository.save(student);
        List singleResultAfter = getListOfCourses();

        //then
        assertEquals(studentId, saveStudent.getId());
        assertEquals(1, saveStudent.getCourses().size());
        assertEquals(courseId, courseRepository.findById(courseId).get().getId());
    }

    @Test
    @Transactional
    @DirtiesContext
    void save_remove_all_course() {
        //given
        long courseId = 10000L;
        long studentId = 20000L;
        Course course = courseRepository.findById(courseId).get();
        Student student = studentRepository.getById(studentId);

        student.removeCourse(course);

        //when
        Student saveStudent = studentRepository.save(student);

        //then
        assertEquals(studentId, saveStudent.getId());
        assertEquals(1, saveStudent.getCourses().size());
        assertEquals(courseId, courseRepository.findById(courseId).get().getId());
    }
}