package com.lego.business.service.employee;

import com.lego.business.service.employee.model.EmployeeBusinessModel;

import java.util.List;

public interface EmployeeService {

  List<EmployeeBusinessModel> findAll();

  EmployeeBusinessModel findByPkId(Long pkId);

  List<EmployeeBusinessModel> findByName(String name);

  EmployeeBusinessModel findByPhoneNumber(String phoneNumber);

  void registerEmployee(EmployeeBusinessModel employeeBusinessModel);

  void deleteEmployee(EmployeeBusinessModel employeeBusinessModel);
}
