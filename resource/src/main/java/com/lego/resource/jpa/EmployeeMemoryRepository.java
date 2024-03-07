package com.lego.resource.jpa;

import com.lego.resource.entity.Employee;

import java.util.List;

public interface EmployeeMemoryRepository {
  List<Employee> findAllInMemory();

  Employee findByPkIdInMemory(Long pkId);

  List<Employee> findByNameInMemory(String name);

  Employee findByPhoneNumberInMemory(String phoneNumber);

  Employee findByEmailInMemory(String email);

  void saveInMemory(Employee employee);

  void deleteInMemory(Employee employee);
}
