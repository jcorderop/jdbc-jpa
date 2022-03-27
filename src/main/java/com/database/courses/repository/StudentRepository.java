package com.database.courses.repository;

import com.database.courses.entity.Course;
import com.database.courses.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class StudentRepository {

    @Autowired
    private EntityManager entityManager;

    public Student findById(final Long id) {
        return entityManager.find(Student.class, id);
    }

    public Student save(final Student student) {
        return entityManager.merge(student);
    }

    public List<Student> insertWithPassport(final Student student) {
        entityManager.persist(student.getPassport());
        entityManager.persist(student);
        return entityManager.createQuery("Select s from Student s where s.name = :name", Student.class)
                .setParameter("name", student.getName())
                .getResultList();
    }

    public void deleteById(final Long id) {
        final Student student = findById(id);
        student.getCourses()
                .stream()
                .toList()
                .stream()
                .forEach(course -> student.removeCourse(course));
        entityManager.merge(student);
        entityManager.remove(student);
    }
}
