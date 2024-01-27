package com.sample.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
