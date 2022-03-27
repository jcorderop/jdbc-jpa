package com.database.courses.repository;

import com.database.courses.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class CourseRepository {

    @Autowired
    private EntityManager entityManager;

    public Course findById(final Long id) {
        return entityManager.find(Course.class, id);
    }

    public Course save(final Course course) {
        return entityManager.merge(course);
    }

    public void deleteById(final Long id) {
        final Course course = findById(id);
        course.getStudents()
                .stream()
                .toList()
                .stream()
                .forEach(student -> course.removeStudent(student));
        entityManager.merge(course);
        entityManager.remove(course);
    }

    public Course persistTest(String name) {
        Course course = new Course("Test");
        entityManager.persist(course);
        course.setName(name);
        return course;
    }

    public Course detachTest(String name) {
        Course course = new Course("Test");
        entityManager.persist(course);
        entityManager.flush();
        entityManager.detach(course);
        course.setName(name);
        entityManager.flush();
        return course;
    }

    public Course clearTest(String name) {
        Course course = new Course("Test");
        entityManager.persist(course);
        entityManager.flush();
        entityManager.clear();
        course.setName(name);
        entityManager.flush();
        return course;
    }

    public Course refreshTest(String name) {
        Course course = new Course("Test");
        entityManager.persist(course);
        entityManager.flush();
        //entityManager.clear();
        course.setName(name);
        entityManager.refresh(course);
        entityManager.flush();
        return course;
    }


}
