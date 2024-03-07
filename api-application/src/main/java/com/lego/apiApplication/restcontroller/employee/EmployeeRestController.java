package com.lego.apiApplication.restcontroller.employee;

import com.lego.apiApplication.restcontroller.model.ResultVo;
import com.lego.business.service.employee.EmployeeReactiveService;
import com.lego.business.service.employee.EmployeeService;
import com.lego.business.service.employee.model.EmployeeBusinessModel;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeRestController {

  private final EmployeeService employeeService;

  private final EmployeeReactiveService employeeReactiveService;

  @GetMapping("/api/employee/v1")
  public Mono<ResultVo<List<EmployeeBusinessModel>>> findEmployeeByPhoneNumberOrName(
    @RequestParam(required = false) String phoneNumber,
    @RequestParam(required = false) String name
  ) {
    return Mono.fromCallable(() -> {
      if (!StringUtils.isEmpty(phoneNumber)) {
        EmployeeBusinessModel employeeBusinessModel = employeeService.findByPhoneNumber(phoneNumber);
        return ResultVo.buildSuccess(Collections.singletonList(employeeBusinessModel));
      }

      if (!StringUtils.isEmpty(name)) {
        List<EmployeeBusinessModel> employeeBusinessModelList = employeeService.findByName(name);
        return ResultVo.buildSuccess(employeeBusinessModelList);
      }

      return ResultVo.buildSuccess();
    });
  }

  @PostMapping("/api/employee/v1")
  public Mono<ResultVo<EmployeeBusinessModel>> registerEmployee(@RequestBody EmployeeBusinessModel employeeBusinessModel) {
    return Mono.fromCallable(() -> {
      employeeService.registerEmployee(employeeBusinessModel);
      return ResultVo.buildSuccess();
    });
  }

  @DeleteMapping("/api/employee/v1")
  public Mono<ResultVo<EmployeeBusinessModel>> deleteEmployee(@RequestBody EmployeeBusinessModel employeeBusinessModel) {
    return Mono.fromCallable(() -> {
      employeeService.deleteEmployee(employeeBusinessModel);
      return ResultVo.buildSuccess();
    });
  }

  @GetMapping("/api/employee/v2")
  public Mono<ResultVo<List<EmployeeBusinessModel>>> findEmployeeByPhoneNumberOrNameV2(
    @RequestParam(required = false) String phoneNumber,
    @RequestParam(required = false) String name
  ) {

    if (!StringUtils.isEmpty(phoneNumber)) {
      return employeeReactiveService.findByPhoneNumber(phoneNumber)
        .map(employee -> ResultVo.buildSuccess(Collections.singletonList(employee)));
    }

    if (!StringUtils.isEmpty(name)) {
      return employeeReactiveService.findByName(name).map(ResultVo::buildSuccess);
    }

    return Mono.just(ResultVo.buildFail("Neither phoneNumber nor name is provided."));
  }

  @PostMapping("/api/employee/v2")
  public Mono<ResultVo<Void>> registerEmployeeV2(@RequestBody EmployeeBusinessModel employeeBusinessModel) {
    return employeeReactiveService.registerEmployee(employeeBusinessModel)
      .then(Mono.just(ResultVo.buildSuccess()));
  }

  @DeleteMapping("/api/employee/v2")
  public Mono<ResultVo<Void>> deleteEmployeeV2(@RequestBody EmployeeBusinessModel employeeBusinessModel) {
    return employeeReactiveService.deleteEmployee(employeeBusinessModel)
      .then(Mono.just(ResultVo.buildSuccess()));
  }
}
