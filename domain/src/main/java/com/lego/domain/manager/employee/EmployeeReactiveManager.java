package com.lego.domain.manager.employee;


import com.lego.domain.manager.employee.model.EmployeeDomainModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeReactiveManager {

  Flux<EmployeeDomainModel> findAll();

  Mono<EmployeeDomainModel> findByPkId(Long pkId);

  Flux<EmployeeDomainModel> findByName(String name);

  Mono<EmployeeDomainModel> findByPhoneNumber(String phoneNumber);

  Mono<EmployeeDomainModel> findByEmail(String email);

  Mono<EmployeeDomainModel> save(EmployeeDomainModel employeeDomainModel);

  Mono<Void> delete(EmployeeDomainModel employeeDomainModel);
}
