package com.lego.resource.jpa;

import com.lego.resource.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  List<Employee> findAllByName(String name);

  Employee findByPhoneNumber(String phoneNumber);

  Employee findByEmail(String email);
}
