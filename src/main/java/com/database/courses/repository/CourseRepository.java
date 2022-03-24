package com.database.courses.repository;

import com.database.courses.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
        entityManager.remove(course);
    }
}
