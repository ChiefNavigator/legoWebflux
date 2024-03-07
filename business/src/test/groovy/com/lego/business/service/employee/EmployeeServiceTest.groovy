package com.lego.business.service.employee

import com.lego.business.service.employee.contants.EmployeeServiceExceptionStatusCode
import com.lego.business.service.employee.contants.EmployeeRegisterExceptionStatusCode
import com.lego.business.service.employee.exception.EmployeeServiceException
import com.lego.business.service.employee.exception.EmployeeDeleteException
import com.lego.business.service.employee.exception.EmployeeRegisterException
import com.lego.business.service.employee.contants.EmployeeDeleteExceptionStatusCode
import com.lego.business.service.employee.model.EmployeeBusinessModel
import com.lego.domain.manager.employee.EmployeeManager
import com.lego.domain.manager.employee.model.EmployeeDomainModel
import spock.lang.Specification

class EmployeeServiceTest extends Specification {

    def employeeManager = Mock(EmployeeManager)

    def employeeService = new EmployeeServiceImpl(
            employeeManager
    )

    def "findAll"() {
        given:
        employeeManager.findAll() >> employeeList

        when:
        def savedEmployeeList = employeeService.findAll()

        then:
        true
        savedEmployeeList == result

        where:
        employeeList                                                                                   | result
        null                                                                                           | Collections.emptyList()
        Arrays.asList(buildEmployeeDomainModel(1L, "가나다", "주소1", "010-1111-1111", "email1@gamil.com")) | Arrays.asList(buildEmployeeBusinessModel(1L, "가나다", "주소1", "010-1111-1111", "email1@gamil.com"))
    }

    def "findByPkId"() {
        given:
        employeeManager.findByPkId(pkId) >> employee

        when:
        def savedEmployee = employeeService.findByPkId(pkId)

        then:
        savedEmployee == result

        where:
        pkId | employee                                                                        | result
        1L   | buildEmployeeDomainModel(1L, "가나다", "주소1", "010-1111-1111", "email1@gamil.com") | buildEmployeeBusinessModel(1L, "가나다", "주소1", "010-1111-1111", "email1@gamil.com")
        2L   | null                                                                            | null
    }

    def "findByPkId - Test whether EmployeeCommonException occurs when findByPkId input value is invalid"() {
        when:
        employeeService.findByPkId(null)

        then:
        def ex = thrown(EmployeeServiceException)
        ex.getStatusCode() == EmployeeServiceExceptionStatusCode.EMPTY_PK_ID.getStatusCode()
        ex.getMessage() == EmployeeServiceExceptionStatusCode.EMPTY_PK_ID.getMessage()
    }

    def "findByName"() {
        given:
        employeeManager.findByName(name) >> employeeList

        when:
        def savedEmployeeList = employeeService.findByName(name)

        then:
        savedEmployeeList == result

        where:
        name  | employeeList                                                                                   | result
        "가나다" | Arrays.asList(buildEmployeeDomainModel(1L, "가나다", "주소1", "010-1111-1111", "email1@gamil.com")) | Arrays.asList(buildEmployeeBusinessModel(1L, "가나다", "주소1", "010-1111-1111", "email1@gamil.com"))
        "나나나" | Collections.emptyList()                                                                        | Collections.emptyList()
    }

    def "findByName - Test whether EmployeeCommonException occurs when findByName input value is invalid"() {
        when:
        employeeService.findByName(null)

        then:
        def ex = thrown(EmployeeServiceException)
        ex.getStatusCode() == EmployeeServiceExceptionStatusCode.EMPTY_NAME.getStatusCode()
        ex.getMessage() == EmployeeServiceExceptionStatusCode.EMPTY_NAME.getMessage()
    }

    def "findByPhoneNumber"() {
        given:
        employeeManager.findByPhoneNumber(phoneNumber) >> employee

        when:
        def savedEmployee = employeeService.findByPhoneNumber(phoneNumber)

        then:
        savedEmployee == result

        where:
        phoneNumber     | employee                                                                        | result
        "010-1111-1111" | buildEmployeeDomainModel(1L, "가나다", "주소1", "010-1111-1111", "email1@gamil.com") | buildEmployeeBusinessModel(1L, "가나다", "주소1", "010-1111-1111", "email1@gamil.com")
        "010-2222-2222" | null                                                                            | null
    }

    def "findByPhoneNumber - Test whether EmployeeCommonException occurs when findByPhoneNumber input value is invalid"() {
        when:
        employeeService.findByPhoneNumber(null)

        then:
        def ex = thrown(EmployeeServiceException)
        ex.getStatusCode() == EmployeeServiceExceptionStatusCode.EMPTY_PHONE_NUMBER.getStatusCode()
        ex.getMessage() == EmployeeServiceExceptionStatusCode.EMPTY_PHONE_NUMBER.getMessage()
    }


    def "registerEmployee"() {
        given:
        def employee = buildEmployeeBusinessModel(1L, "가나다", "주소1", "010-1111-1111", "email1@gamil.com")

        when:
        employeeService.registerEmployee(employee)

        then:
        1 * employeeManager.save(employee.buildEmployeeDomainModel())
    }

    def "registerEmployee - Test whether EmployeeCommonException occurs when registerEmployee input value is invalid"() {
        given:
        employeeManager.findByPhoneNumber("010-1111-1111") >> new EmployeeDomainModel()
        employeeManager.findByEmail("email2@gamil.com") >> new EmployeeDomainModel()

        when:
        employeeService.registerEmployee(employee)

        then:
        def ex = thrown(exception)
        ex.getStatusCode() == exceptionStatusCode.getStatusCode()
        ex.getMessage() == exceptionStatusCode.getMessage()
        0 * employeeManager.save(_)

        where:
        employee                                                                       | exception                | exceptionStatusCode
        null                                                                           | EmployeeServiceException | EmployeeServiceExceptionStatusCode.NULL_MODEL
        buildEmployeeBusinessModel(1L, "", "주소1", "010-1111-1111", "email1@gamil.com") | EmployeeServiceException | EmployeeServiceExceptionStatusCode.EMPTY_NAME
        buildEmployeeBusinessModel(1L, "가나다", "", "010-1111-1111", "email1@gamil.com") | EmployeeServiceException | EmployeeServiceExceptionStatusCode.EMPTY_ADDRESS
        buildEmployeeBusinessModel(1L, "가나다", "주소1", "", "email1@gamil.com")           | EmployeeServiceException | EmployeeServiceExceptionStatusCode.EMPTY_PHONE_NUMBER
        buildEmployeeBusinessModel(1L, "가나다", "주소1", "010-1111-1111", "")              | EmployeeServiceException | EmployeeServiceExceptionStatusCode.EMPTY_EMAIL
    }

    def "registerEmployee - Test whether EmployeeRegisterException occurs when registerEmployee input value is invalid"() {
        given:
        employeeManager.findByPhoneNumber("010-1111-1111") >> new EmployeeDomainModel()
        employeeManager.findByEmail("email2@gamil.com") >> new EmployeeDomainModel()

        when:
        employeeService.registerEmployee(employee)

        then:
        def ex = thrown(exception)
        ex.getStatusCode() == exceptionStatusCode.getStatusCode()
        ex.getMessage() == exceptionStatusCode.getMessage()
        0 * employeeManager.save(_)

        where:
        employee                                                                          | exception                 | exceptionStatusCode
        buildEmployeeBusinessModel(1L, "가나다", "주소1", "01011111111", "email1@gamil.com")   | EmployeeRegisterException | EmployeeRegisterExceptionStatusCode.INVALID_PHONE_NUMBER_FORMAT
        buildEmployeeBusinessModel(1L, "가나다", "주소1", "010-1111-1111", "email1")           | EmployeeRegisterException | EmployeeRegisterExceptionStatusCode.INVALID_EMAIL_FORMAT
        buildEmployeeBusinessModel(1L, "가나다", "주소1", "010-1111-1111", "email1@gamil.com") | EmployeeRegisterException | EmployeeRegisterExceptionStatusCode.DUPLICATED_PHONE_NUMBER
        buildEmployeeBusinessModel(1L, "가나다", "주소1", "010-2222-2222", "email2@gamil.com") | EmployeeRegisterException | EmployeeRegisterExceptionStatusCode.DUPLICATED_EMAIL
    }

    def "deleteEmployee"() {
        given:
        def employee = buildEmployeeBusinessModel(1L, "가나다", "주소1", "010-1111-1111", "email1@gamil.com")
        employeeManager.findByPkId(1L) >> buildEmployeeDomainModel(1L, "가나다", "주소1", "010-1111-1111", "email1@gamil.com")

        when:
        employeeService.deleteEmployee(employee)

        then:
        1 * employeeManager.delete(employee.buildEmployeeDomainModel())
    }

    def "deleteEmployee - Test whether EmployeeCommonException occurs when deleteEmployee input value is invalid"() {
        when:
        employeeService.deleteEmployee(null)

        then:
        def ex = thrown(exception)
        ex.getStatusCode() == exceptionStatusCode.getStatusCode()
        ex.getMessage() == exceptionStatusCode.getMessage()
        0 * employeeManager.delete(_)

        where:
        employee | exception                | exceptionStatusCode
        null     | EmployeeServiceException | EmployeeServiceExceptionStatusCode.NULL_MODEL
    }

    def "deleteEmployee - Test whether EmployeeDeleteException occurs when deleteEmployee input value is invalid"() {
        when:
        employeeService.deleteEmployee(employee)

        then:
        def ex = thrown(exception)
        ex.getStatusCode() == exceptionStatusCode.getStatusCode()
        ex.getMessage() == exceptionStatusCode.getMessage()
        0 * employeeManager.delete(_)

        where:
        employee                                                                            | exception               | exceptionStatusCode
        buildEmployeeBusinessModel(null, "가나다", "주소1", "010-1111-1111", "email1@gamil.com") | EmployeeDeleteException | EmployeeDeleteExceptionStatusCode.NULL_PK_ID
        buildEmployeeBusinessModel(1L, "가나다", "주소1", "010-1111-1111", "email1@gamil.com")   | EmployeeDeleteException | EmployeeDeleteExceptionStatusCode.CAN_NOT_FOUND_EMPLOYEE
    }

    private EmployeeDomainModel buildEmployeeDomainModel(
            Long pkId,
            String name,
            String address,
            String phoneNumber,
            String email
    ) {
        def employeeDomainModel = new EmployeeDomainModel()
        employeeDomainModel.setPkId(pkId)
        employeeDomainModel.setName(name)
        employeeDomainModel.setAddress(address)
        employeeDomainModel.setPhoneNumber(phoneNumber)
        employeeDomainModel.setEmail(email)

        return employeeDomainModel
    }

    private EmployeeBusinessModel buildEmployeeBusinessModel(
            Long pkId,
            String name,
            String address,
            String phoneNumber,
            String email
    ) {
        return EmployeeBusinessModel.builder()
                .pkId(pkId)
                .name(name)
                .address(address)
                .phoneNumber(phoneNumber)
                .email(email)
                .build()
    }

}
