package com.lego.apiApplication.restcontroller.exception;

import com.lego.apiApplication.restcontroller.model.ResultVo;
import com.lego.business.service.employee.exception.EmployeeServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice
public class GlobalRestControllerAdvice {

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Mono<ResultVo<Void>> handleException(Exception exception) {
    log.error("[Internal Server Error] Unexpected Exception", exception);
    return Mono.just(ResultVo.buildFail(exception.getMessage()));
  }

  @ExceptionHandler(EmployeeServiceException.class)
  @ResponseStatus(HttpStatus.OK)
  public Mono<ResultVo<Void>> handleEmployeeServiceException(EmployeeServiceException exception) {
    return Mono.just(ResultVo.buildFail(exception.getMessage()));
  }
}

