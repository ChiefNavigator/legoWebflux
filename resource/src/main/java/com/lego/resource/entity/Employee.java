package com.lego.resource.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
@Table
public class Employee {

  @Id
  @GeneratedValue
  @Column
  private Long pkId;
  @Column
  private String name;
  @Column
  private String address;
  @Column
  private
  String phoneNumber;
  @Column
  private String email;
}
