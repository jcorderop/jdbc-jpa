package com.database.employee.repository;

import com.database.employee.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Repository
@Transactional
public class EmployeeRepository {

    @Autowired
    private EntityManager entityManager;

    public Employee save (Employee employee) {
        entityManager.persist(employee);
        return entityManager.createQuery("select e from Employee e where e.name = :name", Employee.class)
                .setParameter("name", employee.getName())
                .getSingleResult();
    }

    public List<Employee> retrieveAllEmployee () {
        return entityManager.createQuery("select e from Employee e", Employee.class)
                .getResultList();
    }
}
