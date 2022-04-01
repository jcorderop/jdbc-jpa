package com.database.courses.repository;

import com.database.courses.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SpringRepositoryTest {

    @Autowired
    CourseSpringDataRepository courseRepository;

    @Test
    @DirtiesContext
    void sort_search() {
        //given
        Sort sort = Sort.by(Sort.Direction.DESC,"name");

        //when
        List<Course> all = courseRepository.findAll(sort);

        //then
        assertEquals(4, all.size());
        all.forEach(System.out::println);
    }

    @Test
    @DirtiesContext
    void pagination() {
        //given
        for (int i = 0; i < 1000; i++) {
            courseRepository.save(new Course("Course-"+i));
        }
        assertEquals(1004, courseRepository.count());
        //when
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Course> coursePage = courseRepository.findAll(pageRequest);

        //then
        assertEquals(1004, coursePage.getTotalElements());
        assertEquals(101, coursePage.getTotalPages());
        assertEquals(0, coursePage.getNumber());
        assertEquals(10, coursePage.getNumberOfElements());
        coursePage.getContent().forEach(System.out::println);
    }
}
