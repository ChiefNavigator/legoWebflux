package com.lego.business.service.employee.exception;

import com.lego.business.service.employee.contants.EmployeeServiceExceptionStatusCode;
import lombok.Getter;

@Getter
public class EmployeeServiceException extends RuntimeException {

  private final String statusCode;

  private final String message;

  public EmployeeServiceException(EmployeeServiceExceptionStatusCode statusCode) {
    super(statusCode.getMessage());
    this.statusCode = statusCode.getStatusCode();
    this.message = statusCode.getMessage();
  }

  public EmployeeServiceException(String statusCode, String message) {
    super(message);
    this.statusCode = statusCode;
    this.message = message;
  }
}
