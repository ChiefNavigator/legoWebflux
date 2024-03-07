package com.lego.business.service.employee.contants;

import com.lego.business.service.constants.CommonServiceStatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmployeeServiceExceptionStatusCode {

  NULL_MODEL(CommonServiceStatusCode.FAIL, "잘못된 Null Model이 입력되었습니다."),
  EMPTY_PK_ID(CommonServiceStatusCode.FAIL, "잘못된 Null Pk Id가 입력되었습니다."),
  EMPTY_NAME(CommonServiceStatusCode.FAIL, "이름이 비었습니다. 이름을 입력해주세요."),
  EMPTY_ADDRESS(CommonServiceStatusCode.FAIL, "주소가 비었습니다. 주소를 입력해주세요."),
  EMPTY_PHONE_NUMBER(CommonServiceStatusCode.FAIL, "전화번호가 비었습니다. 전화번호를 입력해주세요."),
  EMPTY_EMAIL(CommonServiceStatusCode.FAIL, "이메일이 비었습니다. 이메일을 입력해주세요.");


  private final String statusCode;
  private final String message;
}
