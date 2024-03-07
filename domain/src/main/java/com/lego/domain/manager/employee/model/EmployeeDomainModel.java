package com.lego.domain.manager.employee.model;

import com.lego.infrastructure.maker.DomainModel;
import com.lego.resource.entity.Employee;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@DomainModel
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class EmployeeDomainModel {

  private Long pkId;
  private String name;
  private String address;
  private String phoneNumber;
  private String email;

  public static EmployeeDomainModel of(Employee employee) {
    if (employee == null) {
      return null;
    }

    EmployeeDomainModel employeeDomainModel = new EmployeeDomainModel();
    employeeDomainModel.setPkId(employee.getPkId());
    employeeDomainModel.setName(employee.getName());
    employeeDomainModel.setAddress(employee.getAddress());
    employeeDomainModel.setPhoneNumber(employee.getPhoneNumber());
    employeeDomainModel.setEmail(employee.getEmail());

    return employeeDomainModel;
  }

  public Employee buildEmployee() {
    Employee employee = new Employee();
    employee.setPkId(this.pkId);
    employee.setName(this.name);
    employee.setAddress(this.address);
    employee.setPhoneNumber(this.phoneNumber);
    employee.setEmail(this.email);

    return employee;
  }
}
