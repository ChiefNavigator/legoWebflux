package com.lego.business.service.employee.exception;

import com.lego.business.service.employee.contants.EmployeeRegisterExceptionStatusCode;
import lombok.Getter;

@Getter
public class EmployeeRegisterException extends EmployeeServiceException {

  public EmployeeRegisterException(EmployeeRegisterExceptionStatusCode statusCode) {
    super(statusCode.getStatusCode(), statusCode.getMessage());
  }
}
