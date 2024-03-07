package com.lego.resource.jpa.employee

import com.lego.resource.entity.Employee
import com.lego.resource.jpa.EmployeeRepository
import com.lego.resource.jpa.EmployeeRepositoryImpl
import spock.lang.Specification

class EmployeeRepositoryTest extends Specification {

    private EmployeeRepository employeeRepository = new EmployeeRepositoryImpl()

    def setup() {
        employeeRepository.saveInMemory(buildEmployee(null, "가나다", "주소1", "010-1111-1111", "email1"))
        employeeRepository.saveInMemory(buildEmployee(null, "가가가", "주소1", "010-1111-1111", "email2"))
        employeeRepository.saveInMemory(buildEmployee(null, "나나나", "주소1", "010-2222-2222", "email1"))
        employeeRepository.saveInMemory(buildEmployee(null, "다다다", "주소3", "010-3333-3333", "email3"))
        employeeRepository.saveInMemory(buildEmployee(null, "다다다", "주소4", "010-4444-4444", "email4"))
    }

    def "findAll"() {
        given:
        def result = Arrays.asList(
                buildEmployee(1L, "가나다", "주소1", "010-1111-1111", "email1"),
                buildEmployee(2L, "다다다", "주소3", "010-3333-3333", "email3"),
                buildEmployee(3L, "다다다", "주소4", "010-4444-4444", "email4")
        )

        expect:
        employeeRepository.findAllInMemory() == result
    }

    def "findByPkId"() {
        expect:
        employeeRepository.findByPkIdInMemory(pkId) == result

        where:
        pkId | result
        1L   | buildEmployee(1L, "가나다", "주소1", "010-1111-1111", "email1")
        null | null
    }

    def "findByName"() {
        expect:
        employeeRepository.findByNameInMemory(name) == result

        where:
        name  | result
        "가나다" | Arrays.asList(buildEmployee(1L, "가나다", "주소1", "010-1111-1111", "email1"))
        "가가가" | Collections.emptyList()
        "나나나" | Collections.emptyList()
        "다다다" | Arrays.asList(buildEmployee(2L, "다다다", "주소3", "010-3333-3333", "email3"), buildEmployee(3L, "다다다", "주소4", "010-4444-4444", "email4"))
    }

    def "findByPhoneNumber"() {
        expect:
        employeeRepository.findByPhoneNumberInMemory(name) == result

        where:
        name            | result
        "010-1111-1111" | buildEmployee(1L, "가나다", "주소1", "010-1111-1111", "email1")
        "010-2222-2222" | null
        "010-3333-3333" | buildEmployee(2L, "다다다", "주소3", "010-3333-3333", "email3")
        "010-4444-4444" | buildEmployee(3L, "다다다", "주소4", "010-4444-4444", "email4")
    }

    def "findByEmail"() {
        expect:
        employeeRepository.findByEmailInMemory(name) == result

        where:
        name     | result
        "email1" | buildEmployee(1L, "가나다", "주소1", "010-1111-1111", "email1")
        "email2" | null
        "email3" | buildEmployee(2L, "다다다", "주소3", "010-3333-3333", "email3")
        "email4" | buildEmployee(3L, "다다다", "주소4", "010-4444-4444", "email4")
    }

    def "save"() {
        when:
        employeeRepository.saveInMemory(null)
        employeeRepository.saveInMemory(buildEmployee(null, null, "주소5", "010-5555-5555", "email5"))
        employeeRepository.saveInMemory(buildEmployee(null, "라라라", null, "010-5555-5555", "email5"))
        employeeRepository.saveInMemory(buildEmployee(null, "라라라", "주소5", null, "email5"))
        employeeRepository.saveInMemory(buildEmployee(null, "라라라", "주소5", "010-5555-5555", null))
        employeeRepository.saveInMemory(buildEmployee(null, "마마마", "주소6", "010-6666-6666", "email6"))

        then:
        employeeRepository.findByNameInMemory("라라라") == Collections.emptyList()
        employeeRepository.findByPhoneNumberInMemory("010-5555-5555") == null
        employeeRepository.findByEmailInMemory("email5") == null
        employeeRepository.findByPhoneNumberInMemory("010-6666-6666") == buildEmployee(4L, "마마마", "주소6", "010-6666-6666", "email6")
        employeeRepository.findAllInMemory().size() == 4
    }

    def "save - Test whether deleted PhoneNumber and email can be saved again"() {
        when:
        employeeRepository.deleteInMemory(buildEmployee(1L, null, null, null, null))
        employeeRepository.deleteInMemory(buildEmployee(1L, null, null, null, null))
        employeeRepository.deleteInMemory(buildEmployee(2L, null, null, null, null))
        employeeRepository.saveInMemory(buildEmployee(null, "마마마", "주소6", "010-1111-1111", "email6"))
        employeeRepository.saveInMemory(buildEmployee(null, "라라라", "주소7", "010-7777-7777", "email3"))

        then:
        System.out.print(employeeRepository.findAllInMemory())
        employeeRepository.findByNameInMemory("가나다") == Collections.emptyList()
        employeeRepository.findByPhoneNumberInMemory("010-3333-3333") == null
        employeeRepository.findByEmailInMemory("email1") == null
        employeeRepository.findByPhoneNumberInMemory("010-1111-1111") == buildEmployee(4L, "마마마", "주소6", "010-1111-1111", "email6")
        employeeRepository.findByEmailInMemory("email6") == buildEmployee(4L, "마마마", "주소6", "010-1111-1111", "email6")
        employeeRepository.findByPhoneNumberInMemory("010-7777-7777") == buildEmployee(5L, "라라라", "주소7", "010-7777-7777", "email3")
        employeeRepository.findByEmailInMemory("email3") == buildEmployee(5L, "라라라", "주소7", "010-7777-7777", "email3")
        employeeRepository.findAllInMemory().size() == 3
    }

    def "delete"() {
        when:
        employeeRepository.deleteInMemory(null)
        employeeRepository.deleteInMemory(buildEmployee(null, null, null, null, null))
        employeeRepository.deleteInMemory(buildEmployee(1L, null, null, null, null))
        employeeRepository.deleteInMemory(buildEmployee(1L, null, null, null, null))
        employeeRepository.deleteInMemory(buildEmployee(2L, null, null, null, null))

        then:
        employeeRepository.findByPkIdInMemory(1L) == null
        employeeRepository.findByNameInMemory("가나다") == Collections.emptyList()
        employeeRepository.findByPhoneNumberInMemory("010-1111-1111") == null
        employeeRepository.findByEmailInMemory("email1") == null

        employeeRepository.findByPkIdInMemory(2L) == null
        employeeRepository.findByNameInMemory("다다다") == Arrays.asList(buildEmployee(3L, "다다다", "주소4", "010-4444-4444", "email4"))
        employeeRepository.findByPhoneNumberInMemory("010-3333-3333") == null
        employeeRepository.findByEmailInMemory("email3") == null

        employeeRepository.findAllInMemory().size() == 1
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
