package com.database.courses.repository;

import com.database.courses.entity.Course;
import com.database.courses.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Tuple;
import java.util.List;

public interface CourseSpringDataRepository extends JpaRepository<Course, Long> {

    @Query("select r from Review r where r.course.id = :courseId")
    List<Review> getReviewsByCourse(Long courseId);

    @Query("Select c From Course c where c.students is empty")
    List<Course> getCourseWithoutStudents();

    @Query("Select c From Course c where c.students is not empty")
    List<Course> getCoursesWithStudents();

    @Query(value = "select student_id, course_id from student_course", nativeQuery = true)
    List<Tuple> getStudentsCourses();
}
