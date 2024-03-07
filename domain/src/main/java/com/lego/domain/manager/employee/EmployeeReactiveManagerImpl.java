package com.lego.domain.manager.employee;

import com.lego.domain.manager.employee.model.EmployeeDomainModel;
import com.lego.infrastructure.annotation.DomainManager;
import com.lego.resource.j2dbc.EmployeeReactiveRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@DomainManager
@Slf4j
@RequiredArgsConstructor
public class EmployeeReactiveManagerImpl implements EmployeeReactiveManager {

  private final EmployeeReactiveRepository employeeReactiveRepository;

  @Override
  public Flux<EmployeeDomainModel> findAll() {
    return employeeReactiveRepository.findAll()
      .map(EmployeeDomainModel::of);
  }

  @Override
  public Mono<EmployeeDomainModel> findByPkId(Long pkId) {
    if (pkId == null) {
      log.warn("You can't find employees with null pk id.");
      return Mono.empty();
    }

    return employeeReactiveRepository.findById(pkId)
      .map(EmployeeDomainModel::of);
  }

  @Override
  public Flux<EmployeeDomainModel> findByName(String name) {
    if (StringUtils.isEmpty(name)) {
      log.warn("You can't find employees with empty name.");
      return Flux.empty();
    }

    return employeeReactiveRepository.findAllByName(name)
      .map(EmployeeDomainModel::of)
      .doOnSubscribe((x) -> log.info("doOnSubscribe!!!!!!!!!{}", x));
  }

  @Override
  public Mono<EmployeeDomainModel> findByPhoneNumber(String phoneNumber) {
    if (StringUtils.isEmpty(phoneNumber)) {
      log.warn("You can't find employees with empty phoneNumber.");
      return Mono.empty();
    }

    return employeeReactiveRepository.findByPhoneNumber(phoneNumber)
      .map(EmployeeDomainModel::of);
  }

  @Override
  public Mono<EmployeeDomainModel> findByEmail(String email) {
    if (StringUtils.isEmpty(email)) {
      log.warn("You can't find employees with empty email.");
      return Mono.empty();
    }

    return employeeReactiveRepository.findByEmail(email)
      .map(EmployeeDomainModel::of);
  }

  @Override
  public Mono<EmployeeDomainModel> save(EmployeeDomainModel employeeDomainModel) {
    if (employeeDomainModel == null) {
      log.warn("You can't save employees with null model.");
      return Mono.empty();
    }

    return employeeReactiveRepository.save(employeeDomainModel.buildEmployee()).map(EmployeeDomainModel::of);
  }

  @Override
  public Mono<Void> delete(EmployeeDomainModel employeeDomainModel) {
    if (employeeDomainModel == null) {
      log.warn("You can't delete employees with null model.");
      return Mono.empty();
    }

    if (employeeDomainModel.getPkId() == null) {
      log.warn("You can't delete employees with null pk id.");
      return Mono.empty();
    }

    return employeeReactiveRepository.delete(employeeDomainModel.buildEmployee());
  }

}
