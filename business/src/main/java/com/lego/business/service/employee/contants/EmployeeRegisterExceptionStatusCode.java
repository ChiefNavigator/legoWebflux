package com.lego.business.service.employee.contants;

import com.lego.business.service.constants.CommonServiceStatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmployeeRegisterExceptionStatusCode {

  INVALID_PHONE_NUMBER_FORMAT(CommonServiceStatusCode.FAIL, "잘못된 전화번호 형태는 등록할 수 없습니다."),
  INVALID_EMAIL_FORMAT(CommonServiceStatusCode.FAIL, "잘못된 이메일 형태는 등록할 수 없습니다."),
  DUPLICATED_PHONE_NUMBER(CommonServiceStatusCode.FAIL, "중복된 전화번호가 존재하여 사용할 수 없습니다."),
  DUPLICATED_EMAIL(CommonServiceStatusCode.FAIL, "중복된 이메일이 존재하여 사용할 수 없습니다.");

  private final String statusCode;
  private final String message;
}
