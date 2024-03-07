package com.lego.domain.manager.employee


import com.lego.resource.entity.Employee
import com.lego.resource.jpa.EmployeeRepository
import spock.lang.Specification

class EmployeeManagerTest extends Specification {

    def employeeRepository = Mock(EmployeeRepository)

    def employeeManager = new EmployeeManagerImpl(
            employeeRepository
    )

    def "findAll"() {
        given:
        employeeRepository.findAllInMemory() >> employeeList

        when:
        def savedEmployeeList = employeeManager.findAll()

        then:
        savedEmployeeList == result

        where:
        employeeList                                                              | result
        null                                                                      | Collections.emptyList()
        Arrays.asList(buildEmployee(1L, "가나다", "주소1", "010-1111-1111", "email1")) | Arrays.asList(com.lego.domain.manager.employee.model.EmployeeDomainModel.of(buildEmployee(1L, "가나다", "주소1", "010-1111-1111", "email1")))
    }

    def "findByPkId"() {
        given:
        employeeRepository.findByPkIdInMemory(pkId) >> employee

        when:
        def savedEmployee = employeeManager.findByPkId(pkId)

        then:
        savedEmployee == result

        where:
        pkId | employee                                                   | result
        null | null                                                       | null
        1L   | buildEmployee(1L, "가나다", "주소1", "010-1111-1111", "email1") | com.lego.domain.manager.employee.model.EmployeeDomainModel.of(buildEmployee(1L, "가나다", "주소1", "010-1111-1111", "email1"))
        2L   | null                                                       | null
    }

    def "findByName"() {
        given:
        employeeRepository.findByNameInMemory(name) >> employeeList

        when:
        def savedEmployeeList = employeeManager.findByName(name)

        then:
        savedEmployeeList == result

        where:
        name  | employeeList                                                              | result
        null  | Collections.emptyList()                                                   | Collections.emptyList()
        "가나다" | Arrays.asList(buildEmployee(1L, "가나다", "주소1", "010-1111-1111", "email1")) | Arrays.asList(com.lego.domain.manager.employee.model.EmployeeDomainModel.of(buildEmployee(1L, "가나다", "주소1", "010-1111-1111", "email1")))
        "나나나" | Collections.emptyList()                                                   | Collections.emptyList()
    }

    def "findByPhoneNumber"() {
        given:
        employeeRepository.findByPhoneNumberInMemory(phoneNumber) >> employee

        when:
        def savedEmployee = employeeManager.findByPhoneNumber(phoneNumber)

        then:
        savedEmployee == result

        where:
        phoneNumber     | employee                                                   | result
        ""              | null                                                       | null
        "010-1111-1111" | buildEmployee(1L, "가나다", "주소1", "010-1111-1111", "email1") | com.lego.domain.manager.employee.model.EmployeeDomainModel.of(buildEmployee(1L, "가나다", "주소1", "010-1111-1111", "email1"))
        "010-2222-2222" | null                                                       | null
    }

    def "findByEmail"() {
        given:
        employeeRepository.findByEmailInMemory(email) >> employee

        when:
        def savedEmployee = employeeManager.findByEmail(email)

        then:
        savedEmployee == result

        where:
        email    | employee                                                   | result
        ""       | null                                                       | null
        "email1" | buildEmployee(1L, "가나다", "주소1", "010-1111-1111", "email1") | com.lego.domain.manager.employee.model.EmployeeDomainModel.of(buildEmployee(1L, "가나다", "주소1", "010-1111-1111", "email1"))
        "email2" | null                                                       | null
    }

    def "save"() {
        given:
        def employee = buildEmployee(1L, "가나다", "주소1", "010-1111-1111", "email1")

        when:
        employeeManager.save(com.lego.domain.manager.employee.model.EmployeeDomainModel.of(employee))

        then:
        1 * employeeRepository.saveInMemory(employee)
    }

    def "save - Test whether not to save when employeeDomainModel is null"() {
        when:
        employeeManager.save(null)

        then:
        0 * employeeRepository.saveInMemory(_)
    }

    def "delete"() {
        given:
        def employee = buildEmployee(1L, "가나다", "주소1", "010-1111-1111", "email1")

        when:
        employeeManager.delete(com.lego.domain.manager.employee.model.EmployeeDomainModel.of(employee))

        then:
        1 * employeeRepository.deleteInMemory(employee)
    }

    def "delete - Test whether it is not deleted when employeeDomainModel is null or pk Id is null"() {
        given:
        def employee = buildEmployee(null, "가나다", "주소1", "010-1111-1111", "email1")

        when:
        employeeManager.delete(null)
        employeeManager.delete(com.lego.domain.manager.employee.model.EmployeeDomainModel.of(employee))

        then:
        0 * employeeRepository.deleteInMemory(_)
    }

    private Employee buildEmployee(
            Long pkId,
            String name,
            String address,
            String phoneNumber,
            String email
    ) {
        def employee = new Employee()
        employee.setPkId(pkId)
        employee.setName(name)
        employee.setAddress(address)
        employee.setPhoneNumber(phoneNumber)
        employee.setEmail(email)

        return employee
    }

}
