package com.database.employee.repository;

import com.database.employee.entity.Employee;
import com.database.employee.entity.FullTimeEmployee;
import com.database.employee.entity.PartTimeEmployee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository repository;

    @Test
    @DirtiesContext
    void save() {
        //given
        Employee employee = new PartTimeEmployee("Jorge", new BigDecimal(50.0));
        Employee employee2 = new FullTimeEmployee("Pepe", new BigDecimal(500.0));

        //when
        Employee savedEmployee = repository.save(employee);
        Employee savedEmployee2 = repository.save(employee2);

        //then
        assertEquals("Jorge", savedEmployee.getName());
        assertTrue(savedEmployee instanceof PartTimeEmployee);

        assertEquals("Pepe", savedEmployee2.getName());
        assertTrue(savedEmployee2 instanceof FullTimeEmployee);
    }

    @Test
    void retrieveAllEmployee() {
        //given

        //when

        //then
    }
}