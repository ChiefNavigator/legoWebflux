package com.lego.business.service.employee.model;

import com.lego.infrastructure.maker.BusinessModel;
import com.lego.domain.manager.employee.model.EmployeeDomainModel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@BusinessModel
@EqualsAndHashCode
@Getter
@Builder
public class EmployeeBusinessModel {

  private final Long pkId;
  private final String name;
  private final String address;
  private final String phoneNumber;
  private final String email;

  public static EmployeeBusinessModel of(EmployeeDomainModel employeeDomainModel) {
    if (employeeDomainModel == null) {
      return null;
    }

    return EmployeeBusinessModel.builder()
      .pkId(employeeDomainModel.getPkId())
      .name(employeeDomainModel.getName())
      .address(employeeDomainModel.getAddress())
      .phoneNumber(employeeDomainModel.getPhoneNumber())
      .email(employeeDomainModel.getEmail())
      .build();
  }

  public EmployeeDomainModel buildEmployeeDomainModel() {
    EmployeeDomainModel EmployeeDomainModel = new EmployeeDomainModel();
    EmployeeDomainModel.setPkId(this.pkId);
    EmployeeDomainModel.setName(this.name);
    EmployeeDomainModel.setAddress(this.address);
    EmployeeDomainModel.setPhoneNumber(this.phoneNumber);
    EmployeeDomainModel.setEmail(this.email);

    return EmployeeDomainModel;
  }
}
