package com.database.courses.repository;

import com.database.courses.entity.Passport;
import com.database.courses.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentRepositoryTest {

    @Autowired
    StudentRepository repository;

    @Test
    @DirtiesContext
    void findById() {
        //given
        long id = 20000L;

        //when
        Student student = repository.findById(id);

        //then
        assertEquals("Jorge", student.getName());
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
    void deleteById() {
        //given
        long id = 20000L;

        //when
        repository.deleteById(id);
        Student studentSearch = repository.findById(id);

        //then
        assertNull(studentSearch);
    }
}