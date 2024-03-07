package com.lego.apiApplication.restcontroller.model;

import com.lego.business.service.constants.CommonServiceStatusCode;
import com.lego.infrastructure.maker.ApiApplicationModel;
import lombok.*;
import reactor.core.publisher.Mono;

import java.util.concurrent.Callable;

@ApiApplicationModel
@Getter
@RequiredArgsConstructor(staticName = "of")
public class ResultVo<T> {

  private final String statusCode;

  private final String message;

  private final T data;

  public static <Void> ResultVo<Void> buildSuccess() {
    return ResultVo.of(CommonServiceStatusCode.SUCCESS, null, null);
  }

  public static <T> ResultVo<T> buildSuccess(T data) {
    return ResultVo.of(CommonServiceStatusCode.SUCCESS, null, data);
  }

  public static <T> ResultVo<T> buildFail(String message) {
    return ResultVo.of(CommonServiceStatusCode.FAIL, message, null);
  }
}
