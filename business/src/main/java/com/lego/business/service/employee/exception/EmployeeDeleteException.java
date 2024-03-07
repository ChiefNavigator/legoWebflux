package com.lego.business.service.employee.exception;

import com.lego.business.service.employee.contants.EmployeeDeleteExceptionStatusCode;
import lombok.Getter;

@Getter
public class EmployeeDeleteException extends EmployeeServiceException {

  public EmployeeDeleteException(EmployeeDeleteExceptionStatusCode statusCode) {
    super(statusCode.getStatusCode(), statusCode.getMessage());
  }
}
