package com.lego.business.service.employee;

import com.lego.business.service.employee.contants.EmployeeServiceExceptionStatusCode;
import com.lego.business.service.employee.contants.EmployeeDeleteExceptionStatusCode;
import com.lego.business.service.employee.exception.EmployeeServiceException;
import com.lego.business.service.employee.exception.EmployeeDeleteException;
import com.lego.business.service.employee.exception.EmployeeRegisterException;
import com.lego.business.service.employee.contants.EmployeeRegisterExceptionStatusCode;
import com.lego.business.service.employee.model.EmployeeBusinessModel;
import com.lego.infrastructure.annotation.BusinessService;
import com.lego.domain.manager.employee.EmployeeManager;
import com.lego.domain.manager.employee.model.EmployeeDomainModel;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@BusinessService
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private static final String REGEX_PHONE_NUMBER = "^010-\\d{4}-\\d{4}$";
  private static final String REGEX_EMAIL = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$";
  private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(REGEX_PHONE_NUMBER);
  private static final Pattern EMAIL_PATTERN = Pattern.compile(REGEX_EMAIL);

  private final EmployeeManager employeeManager;

  @Override
  public List<EmployeeBusinessModel> findAll() {
    List<EmployeeDomainModel> employeeDomainModelList = employeeManager.findAll();
    if (CollectionUtils.isEmpty(employeeDomainModelList)) {
      return Collections.emptyList();
    }

    return employeeDomainModelList.stream()
      .filter(Objects::nonNull)
      .map(EmployeeBusinessModel::of)
      .collect(Collectors.toList());
  }

  @Override
  public EmployeeBusinessModel findByPkId(Long pkId) {
    if (pkId == null) {
      throw new EmployeeServiceException(EmployeeServiceExceptionStatusCode.EMPTY_PK_ID);
    }

    EmployeeDomainModel employeeDomainModel = employeeManager.findByPkId(pkId);

    return Optional.ofNullable(employeeDomainModel)
      .map(EmployeeBusinessModel::of)
      .orElse(null);
  }

  @Override
  public List<EmployeeBusinessModel> findByName(String name) {
    if (StringUtils.isEmpty(name)) {
      throw new EmployeeServiceException(EmployeeServiceExceptionStatusCode.EMPTY_NAME);
    }

    List<EmployeeDomainModel> employeeDomainModelList = employeeManager.findByName(name);
    if (CollectionUtils.isEmpty(employeeDomainModelList)) {
      return Collections.emptyList();
    }

    return employeeDomainModelList.stream()
      .filter(Objects::nonNull)
      .map(EmployeeBusinessModel::of)
      .collect(Collectors.toList());
  }

  @Override
  public EmployeeBusinessModel findByPhoneNumber(String phoneNumber) {
    if (StringUtils.isEmpty(phoneNumber)) {
      throw new EmployeeServiceException(EmployeeServiceExceptionStatusCode.EMPTY_PHONE_NUMBER);
    }

    EmployeeDomainModel employeeDomainModel = employeeManager.findByPhoneNumber(phoneNumber);

    return Optional.ofNullable(employeeDomainModel)
      .map(EmployeeBusinessModel::of)
      .orElse(null);
  }

  @Override
  public void registerEmployee(EmployeeBusinessModel employeeBusinessModel) {
    checkRegisterEmployeeBusinessModel(employeeBusinessModel);

    EmployeeDomainModel employeeDomainModel = employeeBusinessModel.buildEmployeeDomainModel();
    employeeManager.save(employeeDomainModel);
  }

  private void checkRegisterEmployeeBusinessModel(EmployeeBusinessModel employeeBusinessModel) {
    if (employeeBusinessModel == null) {
      throw new EmployeeServiceException(EmployeeServiceExceptionStatusCode.NULL_MODEL);
    }

    if (StringUtils.isEmpty(employeeBusinessModel.getName())) {
      throw new EmployeeServiceException(EmployeeServiceExceptionStatusCode.EMPTY_NAME);
    }

    if (StringUtils.isEmpty(employeeBusinessModel.getAddress())) {
      throw new EmployeeServiceException(EmployeeServiceExceptionStatusCode.EMPTY_ADDRESS);
    }

    if (StringUtils.isEmpty(employeeBusinessModel.getPhoneNumber())) {
      throw new EmployeeServiceException(EmployeeServiceExceptionStatusCode.EMPTY_PHONE_NUMBER);
    }

    if (StringUtils.isEmpty(employeeBusinessModel.getEmail())) {
      throw new EmployeeServiceException(EmployeeServiceExceptionStatusCode.EMPTY_EMAIL);
    }

    if (!PHONE_NUMBER_PATTERN.matcher(employeeBusinessModel.getPhoneNumber()).matches()) {
      throw new EmployeeRegisterException(EmployeeRegisterExceptionStatusCode.INVALID_PHONE_NUMBER_FORMAT);
    }

    if (!EMAIL_PATTERN.matcher(employeeBusinessModel.getEmail()).matches()) {
      throw new EmployeeRegisterException(EmployeeRegisterExceptionStatusCode.INVALID_EMAIL_FORMAT);
    }

    EmployeeDomainModel phoneNumberEmployeeModel = employeeManager.findByPhoneNumber(employeeBusinessModel.getPhoneNumber());
    if (phoneNumberEmployeeModel != null) {
      throw new EmployeeRegisterException(EmployeeRegisterExceptionStatusCode.DUPLICATED_PHONE_NUMBER);
    }

    EmployeeDomainModel emailEmployeeModel = employeeManager.findByEmail(employeeBusinessModel.getEmail());
    if (emailEmployeeModel != null) {
      throw new EmployeeRegisterException(EmployeeRegisterExceptionStatusCode.DUPLICATED_EMAIL);
    }
  }


  @Override
  public void deleteEmployee(EmployeeBusinessModel employeeBusinessModel) {
    if (employeeBusinessModel == null) {
      throw new EmployeeServiceException(EmployeeServiceExceptionStatusCode.NULL_MODEL);
    }

    if (employeeBusinessModel.getPkId() == null) {
      throw new EmployeeDeleteException(EmployeeDeleteExceptionStatusCode.NULL_PK_ID);
    }

    EmployeeDomainModel employeeDomainModel = employeeManager.findByPkId(employeeBusinessModel.getPkId());
    if (employeeDomainModel == null) {
      throw new EmployeeDeleteException(EmployeeDeleteExceptionStatusCode.CAN_NOT_FOUND_EMPLOYEE);
    }

    employeeManager.delete(employeeDomainModel);
  }
}
