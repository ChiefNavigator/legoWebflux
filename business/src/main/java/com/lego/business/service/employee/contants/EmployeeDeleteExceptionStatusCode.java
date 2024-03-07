package com.lego.business.service.employee.contants;

import com.lego.business.service.constants.CommonServiceStatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmployeeDeleteExceptionStatusCode {

  NULL_PK_ID(CommonServiceStatusCode.FAIL, "NULL PK ID는 삭제할 수 없습니다."),
  CAN_NOT_FOUND_EMPLOYEE(CommonServiceStatusCode.FAIL, "고객정보를 찾을 수 없습니다.");

  private final String statusCode;
  private final String message;
}
