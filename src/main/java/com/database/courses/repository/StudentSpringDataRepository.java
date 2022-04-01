package com.database.courses.repository;

import com.database.courses.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(path = "students")
public interface StudentSpringDataRepository  extends JpaRepository<Student, Long> {
}
