package com.lego.domain.manager.employee;

import com.lego.domain.manager.employee.model.EmployeeDomainModel;

import java.util.List;

public interface EmployeeManager {

  List<EmployeeDomainModel> findAll();

  EmployeeDomainModel findByPkId(Long pkId);

  List<EmployeeDomainModel> findByName(String name);

  EmployeeDomainModel findByPhoneNumber(String phoneNumber);

  EmployeeDomainModel findByEmail(String email);

  void save(EmployeeDomainModel employeeDomainModel);

  void delete(EmployeeDomainModel employeeDomainModel);
}
