package com.lego.resource.jpa;

import com.lego.infrastructure.annotation.ResourceRepository;
import com.lego.resource.datastructure.Trie;
import com.lego.resource.entity.Employee;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@ResourceRepository
@Slf4j
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeMemoryRepository {
  private final Map<Long, Employee> employeeMap = new HashMap<>();
  private final Trie nameTrie = new Trie();
  private final Trie phoneNumberTrie = new Trie();
  private final Trie emailTrie = new Trie();
  private Long pkId = 0L;

  @Override
  public List<Employee> findAllInMemory() {
    return employeeMap.values().stream().toList();
  }

  @Override
  public Employee findByPkIdInMemory(Long pkId) {
    return employeeMap.getOrDefault(pkId, null);
  }

  @Override
  public List<Employee> findByNameInMemory(String name) {
    List<Long> pkIdList = nameTrie.find(name);
    if (CollectionUtils.isEmpty(pkIdList)) {
      return Collections.emptyList();
    }

    return pkIdList.stream()
      .filter(Objects::nonNull)
      .filter(employeeMap::containsKey)
      .map(employeeMap::get)
      .collect(Collectors.toList());
  }

  @Override
  public Employee findByPhoneNumberInMemory(String phoneNumber) {
    List<Long> pkIdList = phoneNumberTrie.find(phoneNumber);
    if (CollectionUtils.isEmpty(pkIdList)) {
      return null;
    }

    Employee employee = employeeMap.get(pkIdList.get(0));
    log.info(employeeMap.toString());
    log.info(nameTrie.find(employee.getName()).toString());
    log.info(phoneNumberTrie.find(employee.getPhoneNumber()).toString());
    log.info(emailTrie.find(employee.getEmail()).toString());

    return pkIdList.stream()
      .filter(Objects::nonNull)
      .filter(employeeMap::containsKey)
      .map(employeeMap::get)
      .findAny()
      .orElse(null);
  }

  @Override
  public Employee findByEmailInMemory(String email) {
    List<Long> pkIdList = emailTrie.find(email);
    if (CollectionUtils.isEmpty(pkIdList)) {
      return null;
    }

    return pkIdList.stream()
      .filter(Objects::nonNull)
      .filter(employeeMap::containsKey)
      .map(employeeMap::get)
      .findAny()
      .orElse(null);
  }

  @Override
  public void saveInMemory(Employee employee) {
    if (isInvalidEmployeeToSave(employee)) {
      return;
    }

    pkId = pkId + 1;
    employee.setPkId(pkId);
    employeeMap.put(pkId, employee);
    nameTrie.save(employee.getName(), pkId);
    phoneNumberTrie.save(employee.getPhoneNumber(), pkId);
    emailTrie.save(employee.getEmail(), pkId);
  }

  private boolean isInvalidEmployeeToSave(Employee employee) {
    if (employee == null) {
      log.error("Failed to save employee. [Cause: null model]");
      return true;
    }

    if (StringUtils.isEmpty(employee.getName())) {
      log.error("Failed to save employee. [Cause: empty name]");
      return true;
    }

    if (StringUtils.isEmpty(employee.getAddress())) {
      log.error("Failed to save employee. [Cause: empty address]");
      return true;
    }

    if (StringUtils.isEmpty(employee.getPhoneNumber())) {
      log.error("Failed to save employee. [Cause: empty phoneNumber]");
      return true;
    }

    if (StringUtils.isEmpty(employee.getEmail())) {
      log.error("Failed to save employee. [Cause: empty email]");
      return true;
    }

    Employee phoneNumberEmployee = findByPhoneNumberInMemory(employee.getPhoneNumber());
    if (phoneNumberEmployee != null) {
      log.error("Failed to save employee. [Cause: '{}' is duplicate phoneNumber]", employee.getPhoneNumber());
      return true;
    }

    Employee emailEmployee = findByEmailInMemory(employee.getEmail());
    if (emailEmployee != null) {
      log.error("Failed to save employee. [Cause: '{}' is duplicate email]", emailEmployee.getEmail());
      return true;
    }

    return false;
  }

  @Override
  public void deleteInMemory(Employee employee) {
    if (employee == null) {
      log.error("Failed to delete employee. [Cause: null model]");
      return;
    }

    if (employee.getPkId() == null) {
      log.error("Failed to delete employee. [Cause: null pk id]");
      return;
    }

    Employee savedEmployee = findByPkIdInMemory(employee.getPkId());
    if (savedEmployee == null) {
      log.error("Failed to delete employee. [Cause: employee with pk id '{}' can't be found]", employee.getPkId());
      return;
    }

    nameTrie.delete(savedEmployee.getName(), savedEmployee.getPkId());
    phoneNumberTrie.delete(savedEmployee.getPhoneNumber(), savedEmployee.getPkId());
    emailTrie.delete(savedEmployee.getEmail(), savedEmployee.getPkId());
    employeeMap.remove(savedEmployee.getPkId());
  }
}
