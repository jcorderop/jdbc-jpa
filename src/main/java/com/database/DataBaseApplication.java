package com.database;

import com.database.courses.repository.CourseRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
		excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.database.jdbc_jpa.*")
)
public class DataBaseApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext appCx = SpringApplication.run(DataBaseApplication.class, args);

		CourseRepository courseRepository =  appCx.getBean(CourseRepository.class);
	}

}
