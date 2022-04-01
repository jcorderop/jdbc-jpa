package com.database.courses.repository;

import com.database.courses.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CriteriaQueryTest {

    @Autowired
    EntityManager entityManager;

    @Test
    void getAllCourses() {
        //given
        //1. criteria builder
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        //1.1. criteria builder cast to
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
        //2. from tables/entity
        Root<Course> queryRoot = criteriaQuery.from(Course.class);
        //3. define predicate
        //4. set predicate
        //5. create a TypeQuery using the criteria builder
        List<Course> courses = entityManager
                .createQuery(criteriaQuery.select(queryRoot))
                .getResultList();
        //when

        //then
        assertEquals(4, courses.size());
        courses.forEach(course -> {
            System.out.println(course.toString());
        });
    }

    @Test
    void getAllCoursesLike() {
        //given
        String name = "%e%";

        //1. criteria builder
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        //1.1. criteria builder cast to
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
        //2. from tables/entity
        Root<Course> queryRoot = criteriaQuery.from(Course.class);
        //3. define predicate
        Predicate predicate = criteriaBuilder.like(queryRoot.get("name"), name);
        //4. set predicate
        criteriaQuery.where(predicate);
        //5. create a TypeQuery using the criteria builder
        List<Course> courses = entityManager
                .createQuery(criteriaQuery.select(queryRoot))
                .getResultList();
        //when

        //then
        assertEquals(2, courses.size());
        courses.forEach(course -> {
            System.out.println(course.toString());
        });
    }

    @Test
    void getAllCoursesWithoutStudents() {
        //given

        //1. criteria builder
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        //1.1. criteria builder cast to
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
        //2. from tables/entity
        Root<Course> queryRoot = criteriaQuery.from(Course.class);
        //3. define predicate
        Predicate predicate = criteriaBuilder.isEmpty(queryRoot.get("students"));
        //4. set predicate
        criteriaQuery.where(predicate);
        //5. create a TypeQuery using the criteria builder
        List<Course> courses = entityManager
                .createQuery(criteriaQuery.select(queryRoot))
                .getResultList();
        //when

        //then
        assertEquals(1, courses.size());
        courses.forEach(course -> {
            System.out.println(course.toString());
        });
    }

    @Test
    @Transactional
    void getAllCoursesJoinStudents() {
        //given

        //1. criteria builder
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        //1.1. criteria builder cast to
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
        //2. from tables/entity
        Root<Course> queryRoot = criteriaQuery.from(Course.class);
        //3. define predicate
        Join<Object, Object> students = queryRoot.join("students");
        //4. set predicate
        //5. create a TypeQuery using the criteria builder
        List<Course> courses = entityManager
                .createQuery(criteriaQuery.select(queryRoot))
                .getResultList();
        //when

        //then
        assertEquals(5, courses.size());
        courses.forEach(course -> {
            System.out.println(course.toString());
            course.getStudents().forEach(System.out::println);
        });
    }

    @Test
    @Transactional
    void getAllCoursesLeftJoinStudents() {
        //given

        //1. criteria builder
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        //1.1. criteria builder cast to
        CriteriaQuery<Course> criteriaQuery = criteriaBuilder.createQuery(Course.class);
        //2. from tables/entity
        Root<Course> queryRoot = criteriaQuery.from(Course.class);
        //3. define predicate
        Join<Object, Object> students = queryRoot.join("students", JoinType.LEFT);
        //4. set predicate
        //5. create a TypeQuery using the criteria builder
        List<Course> courses = entityManager
                .createQuery(criteriaQuery.select(queryRoot))
                .getResultList();
        //when

        //then
        assertEquals(6, courses.size());
        courses.forEach(course -> {
            System.out.println(course.toString());
            course.getStudents().forEach(System.out::println);
        });
    }
}
