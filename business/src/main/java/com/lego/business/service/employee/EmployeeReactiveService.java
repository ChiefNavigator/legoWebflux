package com.lego.business.service.employee;

import com.lego.business.service.employee.model.EmployeeBusinessModel;
import reactor.core.publisher.Mono;

import java.util.List;


public interface EmployeeReactiveService {

  Mono<List<EmployeeBusinessModel>> findAll();

  Mono<EmployeeBusinessModel> findByPkId(Long pkId);

  Mono<List<EmployeeBusinessModel>> findByName(String name);

  Mono<EmployeeBusinessModel> findByPhoneNumber(String phoneNumber);

  Mono<Void> registerEmployee(EmployeeBusinessModel employeeBusinessModel);

  Mono<Void> registerEmployee(List<EmployeeBusinessModel> employeeBusinessModel);

  Mono<Void> deleteEmployee(EmployeeBusinessModel employeeBusinessModel);
}
