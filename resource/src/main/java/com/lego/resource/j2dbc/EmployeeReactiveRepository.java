package com.lego.resource.j2dbc;

import com.lego.resource.entity.Employee;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeReactiveRepository extends R2dbcRepository<Employee, Long> {
  Flux<Employee> findAllByName(String name);

  Mono<Employee> findByPhoneNumber(String phoneNumber);

  Mono<Employee> findByEmail(String email);
}
