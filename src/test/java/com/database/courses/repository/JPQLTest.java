package com.database.courses.repository;

import com.database.courses.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JPQLTest {

    @Autowired
    EntityManager entityManager;

    //jpql
    @Test
    void findAll() {
        //given

        //when
        final List<Course> result = entityManager.createQuery("select c from Course c")
                .setMaxResults(10)
                .getResultList();

        //then
        assertTrue(result.size() > 0);
    }

    @Test
    void findAllTyped() {
        //given

        //when
        final List<Course> result = entityManager.createNamedQuery("all_courses", Course.class)
                .setMaxResults(10)
                .getResultList();

        //then
        assertTrue(result.size() > 0);
    }

    @Test
    void findAllByNameLike() {
        //given
        String name = "Ger%";

        //when
        final List<Course> result = entityManager.createQuery("select c from Course c where c.name like :name", Course.class)
                .setParameter("name", name)
                .setMaxResults(10)
                .getResultList();

        //then
        assertEquals(1, result.size());
        assertEquals(10000L, result.get(0).getId());
        assertEquals("German", result.get(0).getName());
    }

    @Test
    void findByName() {
        //given
        String name = "German";

        //when
        final List<Course> result = entityManager.createQuery("select c from Course c where c.name = :name", Course.class)
                .setParameter("name", name)
                .getResultList();

        //then
        assertEquals(1, result.size());
        assertEquals(10000L, result.get(0).getId());
        assertEquals("German", result.get(0).getName());
    }

    @Test
    void findById() {
        //given
        Long id = 10000L;

        //when
        final List<Course> result = entityManager.createQuery("select c from Course c where c.id = :id ", Course.class)
                .setParameter("id", id)
                .getResultList();

        //then
        assertEquals(1, result.size());
        assertEquals(10000L, result.get(0).getId());
        assertEquals("German", result.get(0).getName());
    }


    @Test
    void findCoursesWithoutStudents () {
        //given

        //when
        List<Course> courses = entityManager
                .createQuery("Select c From Course c where c.students is empty", Course.class)
                .getResultList();
        //then
        assertEquals(1, courses.size());
        assertEquals(10003L, courses.get(0).getId());
    }

    @Test
    void findCoursesWithMoreThan2Students () {
        //given

        //when
        List<Course> courses = entityManager
                .createQuery("Select c From Course c where size(c.students) >= 2", Course.class)
                .getResultList();
        //then
        assertEquals(2, courses.size());
        courses.forEach(System.out::println);
    }

    @Test
    void findCoursesOrderByStudents () {
        //given

        //when
        List<Course> courses = entityManager
                .createQuery("Select c From Course c order by size(c.students) desc", Course.class)
                .getResultList();
        //then
        assertEquals(4, courses.size());
        courses.forEach(System.out::println);
    }

    @Test
    void findCoursesLikeStudents () {
        //given
        String name = "%E%";

        //when
        List<Course> courses = entityManager
                .createQuery("Select c From Course c where upper(c.name) LIKE :name", Course.class)
                .setParameter("name", name)
                .getResultList();
        //then
        assertEquals(3, courses.size());
        courses.forEach(System.out::println);
    }

    //return courses only with students
    @Test
    @Transactional
    void findCoursesJoinStudents () {
        //given

        //when
        List<Course> courses = entityManager
                .createQuery("Select c From Course c join c.students s", Course.class)
                .getResultList();
        //then
        assertEquals(5, courses.size());
        courses.forEach(course -> {
            System.out.println(course.toString());
            course.getStudents().forEach(System.out::println);
        });
    }

    //return courses without students
    @Test
    @Transactional
    void findCoursesLeftJoinStudents () {
        //given

        //when
        List<Course> courses = entityManager
                .createQuery("Select c From Course c left join c.students s", Course.class)
                .getResultList();
        //then
        assertEquals(6, courses.size());
        courses.forEach(course -> {
            System.out.println(course.toString());
            course.getStudents().forEach(System.out::println);
        });
    }

    //returns all
    @Test
    void findCoursesCrossJoinStudents () {
        //given

        //when
        List<Object> courses = entityManager
                .createQuery("Select c, s From Course c, Student s")
                .getResultList();
        //then
        assertEquals(12, courses.size());
        courses.forEach(System.out::println);
    }

}
