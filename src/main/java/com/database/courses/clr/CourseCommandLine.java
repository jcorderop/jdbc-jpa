package com.database.courses.clr;

import com.database.courses.entity.Course;
import com.database.courses.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CourseCommandLine implements CommandLineRunner {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void run(String... args) {
        Course course = courseRepository.findById(10000L);
        log.info(course.toString());
    }
}
