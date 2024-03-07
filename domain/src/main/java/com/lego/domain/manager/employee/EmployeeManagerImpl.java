package com.lego.domain.manager.employee;

import com.lego.domain.manager.employee.model.EmployeeDomainModel;
import com.lego.infrastructure.annotation.DomainManager;
import com.lego.resource.entity.Employee;
import com.lego.resource.jpa.EmployeeRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@DomainManager
@Slf4j
@RequiredArgsConstructor
public class EmployeeManagerImpl implements EmployeeManager {

  @Value("${inMemoryDbMode}")
  private boolean inMemoryDbMode;

  private final EmployeeRepository employeeRepository;

  @Override
  public List<EmployeeDomainModel> findAll() {
    List<Employee> employeeList;
    if (inMemoryDbMode) {
      employeeList = employeeRepository.findAllInMemory();
    } else {
      employeeList = employeeRepository.findAll();
    }

    if (CollectionUtils.isEmpty(employeeList)) {
      return Collections.emptyList();
    }

    return employeeList.stream()
      .filter(Objects::nonNull)
      .map(EmployeeDomainModel::of)
      .collect(Collectors.toList());
  }

  @Override
  public EmployeeDomainModel findByPkId(Long pkId) {
    if (pkId == null) {
      log.warn("You can't find employees with null pk id.");
      return null;
    }

    Optional<Employee> optionalEmployee;
    if (inMemoryDbMode) {
      optionalEmployee = Optional.ofNullable(employeeRepository.findByPkIdInMemory(pkId));
    } else {
      optionalEmployee = employeeRepository.findById(pkId);
    }

    return optionalEmployee
      .map(EmployeeDomainModel::of)
      .orElse(null);
  }

  @Override
  public List<EmployeeDomainModel> findByName(String name) {
    if (StringUtils.isEmpty(name)) {
      log.warn("You can't find employees with empty names.");
      return Collections.emptyList();
    }

    List<Employee> employeeList;
    if (inMemoryDbMode) {
      employeeList = employeeRepository.findByNameInMemory(name);
    } else {
      employeeList = employeeRepository.findAllByName(name);
    }

    if (CollectionUtils.isEmpty(employeeList)) {
      return Collections.emptyList();
    }

    return employeeList.stream()
      .filter(Objects::nonNull)
      .map(EmployeeDomainModel::of)
      .collect(Collectors.toList());
  }

  @Override
  public EmployeeDomainModel findByPhoneNumber(String phoneNumber) {
    if (StringUtils.isEmpty(phoneNumber)) {
      log.warn("You can't find employees with empty phoneNumber.");
      return null;
    }

    Employee employee;
    if (inMemoryDbMode) {
      employee = employeeRepository.findByPhoneNumberInMemory(phoneNumber);
    } else {
      employee = employeeRepository.findByPhoneNumber(phoneNumber);
    }

    return Optional.ofNullable(employee)
      .map(EmployeeDomainModel::of)
      .orElse(null);
  }

  @Override
  public EmployeeDomainModel findByEmail(String email) {
    if (StringUtils.isEmpty(email)) {
      log.warn("You can't find employees with empty email.");
      return null;
    }

    Employee employee;
    if (inMemoryDbMode) {
      employee = employeeRepository.findByEmailInMemory(email);
    } else {
      employee = employeeRepository.findByEmail(email);
    }


    return Optional.ofNullable(employee)
      .map(EmployeeDomainModel::of)
      .orElse(null);
  }

  @Override
  public void save(EmployeeDomainModel employeeDomainModel) {
    if (employeeDomainModel == null) {
      log.warn("You can't save employees with null model.");
      return;
    }

    Employee employee = employeeDomainModel.buildEmployee();
    if (inMemoryDbMode) {
      employeeRepository.saveInMemory(employee);
    } else {
      employeeRepository.save(employee);
    }
  }

  @Override
  public void delete(EmployeeDomainModel employeeDomainModel) {
    if (employeeDomainModel == null) {
      log.warn("You can't delete employees with null model.");
      return;
    }

    if (employeeDomainModel.getPkId() == null) {
      log.warn("You can't delete employees with null pk id.");
      return;
    }

    Employee employee = employeeDomainModel.buildEmployee();
    if (inMemoryDbMode) {
      employeeRepository.deleteInMemory(employee);
    } else {
      employeeRepository.delete(employee);
    }
  }
}
