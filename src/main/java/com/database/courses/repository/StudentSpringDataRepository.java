package com.database.courses.repository;

import com.database.courses.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentSpringDataRepository  extends JpaRepository<Student, Long> {
}
