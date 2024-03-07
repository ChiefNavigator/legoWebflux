package com.lego.business.service.employee;

import com.lego.business.service.employee.contants.EmployeeDeleteExceptionStatusCode;
import com.lego.business.service.employee.contants.EmployeeRegisterExceptionStatusCode;
import com.lego.business.service.employee.contants.EmployeeServiceExceptionStatusCode;
import com.lego.business.service.employee.exception.EmployeeDeleteException;
import com.lego.business.service.employee.exception.EmployeeRegisterException;
import com.lego.business.service.employee.exception.EmployeeServiceException;
import com.lego.business.service.employee.model.EmployeeBusinessModel;
import com.lego.domain.manager.employee.EmployeeReactiveManager;
import com.lego.infrastructure.annotation.BusinessService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.regex.Pattern;

@BusinessService
@Slf4j
@RequiredArgsConstructor
public class EmployeeReactiveServiceImpl implements EmployeeReactiveService {

  private static final String REGEX_PHONE_NUMBER = "^010-\\d{4}-\\d{4}$";
  private static final String REGEX_EMAIL = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$";
  private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(REGEX_PHONE_NUMBER);
  private static final Pattern EMAIL_PATTERN = Pattern.compile(REGEX_EMAIL);

  private final EmployeeReactiveManager employeeReactiveManager;


  @Override
  public Mono<List<EmployeeBusinessModel>> findAll() {
    return employeeReactiveManager.findAll().map(EmployeeBusinessModel::of).collectList();
  }

  @Override
  public Mono<EmployeeBusinessModel> findByPkId(Long pkId) {
    if (pkId == null) {
      return Mono.error(new EmployeeServiceException(EmployeeServiceExceptionStatusCode.EMPTY_PK_ID));
    }

    return employeeReactiveManager.findByPkId(pkId).map(EmployeeBusinessModel::of);
  }

  @Override
  public Mono<List<EmployeeBusinessModel>> findByName(String name) {
    if (StringUtils.isEmpty(name)) {
      return Mono.error(new EmployeeServiceException(EmployeeServiceExceptionStatusCode.EMPTY_NAME));
    }

    return employeeReactiveManager.findByName(name).map(EmployeeBusinessModel::of).collectList();
  }

  @Override
  public Mono<EmployeeBusinessModel> findByPhoneNumber(String phoneNumber) {
    if (StringUtils.isEmpty(phoneNumber)) {
      return Mono.error(new EmployeeServiceException(EmployeeServiceExceptionStatusCode.EMPTY_PHONE_NUMBER));
    }

    return employeeReactiveManager.findByPhoneNumber(phoneNumber)
      .doOnNext((x) -> log.info("object{}", x))
      .map(EmployeeBusinessModel::of);
  }

  @Override
  public Mono<Void> registerEmployee(EmployeeBusinessModel employeeBusinessModel) {
    return checkEmployeeBusinessModel(employeeBusinessModel)
      .then(
        Mono.when(
          checkDuplicatedPhoneNumber(employeeBusinessModel.getPhoneNumber()),
          checkDuplicatedEmail(employeeBusinessModel.getEmail())
        )
      )
      .then(Mono.defer(() -> employeeReactiveManager.save(employeeBusinessModel.buildEmployeeDomainModel())))
      .doOnSubscribe(result -> log.info("doOnSubscribe"))
      .doOnSuccess(result -> log.info("Employee saved successfully!"))
      .doOnError(ex -> log.error("Error occurred while saving employee", ex))
      .then();
  }

  @Override
  public Mono<Void> registerEmployee(List<EmployeeBusinessModel> employeeBusinessModelList) {
    return Flux.fromIterable(employeeBusinessModelList)
      .flatMap(this::registerEmployee)
      .then();
  }

  public Mono<Void> checkEmployeeBusinessModel(EmployeeBusinessModel employeeBusinessModel) {
    if (employeeBusinessModel == null) {
      return Mono.error(new EmployeeServiceException(EmployeeServiceExceptionStatusCode.NULL_MODEL));
    }

    if (StringUtils.isEmpty(employeeBusinessModel.getName())) {
      return Mono.error(new EmployeeServiceException(EmployeeServiceExceptionStatusCode.EMPTY_NAME));
    }

    if (StringUtils.isEmpty(employeeBusinessModel.getAddress())) {
      return Mono.error(new EmployeeServiceException(EmployeeServiceExceptionStatusCode.EMPTY_ADDRESS));
    }

    if (StringUtils.isEmpty(employeeBusinessModel.getPhoneNumber())) {
      return Mono.error(new EmployeeServiceException(EmployeeServiceExceptionStatusCode.EMPTY_PHONE_NUMBER));
    }

    if (StringUtils.isEmpty(employeeBusinessModel.getEmail())) {
      return Mono.error(new EmployeeServiceException(EmployeeServiceExceptionStatusCode.EMPTY_EMAIL));
    }

    if (!PHONE_NUMBER_PATTERN.matcher(employeeBusinessModel.getPhoneNumber()).matches()) {
      return Mono.error(new EmployeeRegisterException(EmployeeRegisterExceptionStatusCode.INVALID_PHONE_NUMBER_FORMAT));
    }

    if (!EMAIL_PATTERN.matcher(employeeBusinessModel.getEmail()).matches()) {
      return Mono.error(new EmployeeRegisterException(EmployeeRegisterExceptionStatusCode.INVALID_EMAIL_FORMAT));
    }

    return Mono.empty();
  }

  private Mono<Void> checkDuplicatedPhoneNumber(String phoneNumber) {
    return employeeReactiveManager.findByPhoneNumber(phoneNumber)
      .flatMap(hasElement -> Mono.error(new EmployeeRegisterException(EmployeeRegisterExceptionStatusCode.DUPLICATED_PHONE_NUMBER)));
  }

  private Mono<Void> checkDuplicatedEmail(String email) {
    return employeeReactiveManager.findByEmail(email)
      .flatMap(hasElement -> Mono.error(new EmployeeRegisterException(EmployeeRegisterExceptionStatusCode.DUPLICATED_EMAIL)));
  }

  @Override
  public Mono<Void> deleteEmployee(EmployeeBusinessModel employeeBusinessModel) {
    if (employeeBusinessModel == null) {
      return Mono.error(new EmployeeServiceException(EmployeeServiceExceptionStatusCode.NULL_MODEL));
    }

    if (employeeBusinessModel.getPkId() == null) {
      return Mono.error(new EmployeeDeleteException(EmployeeDeleteExceptionStatusCode.NULL_PK_ID));
    }

    return employeeReactiveManager.delete(employeeBusinessModel.buildEmployeeDomainModel());
  }
}
