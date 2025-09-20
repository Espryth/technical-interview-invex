package me.javierca.interviews.invex.repository;

import me.javierca.interviews.invex.model.employee.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class EmployeeRepositoryTest {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Test
  void testFindByIdAndDeletedIsFalse() {
    final var employee = Employee.builder()
        .deleted(true)
        .build();
    this.employeeRepository.save(employee);
    assertTrue(this.employeeRepository.findById(employee.getId()).isPresent());
    assertFalse(this.employeeRepository.findByIdAndDeletedIsFalse(employee.getId()).isPresent());
  }

  @Test
  void testFindAllByDeletedIsFalse( ) {
    final var employee1 = Employee.builder()
        .deleted(false)
        .build();
    final var employee2 = Employee.builder()
        .deleted(true)
        .build();
    this.employeeRepository.saveAll(List.of(employee1, employee2));
    final var employees = this.employeeRepository.findAllByDeletedIsFalse();
    assertEquals(employee1.getId(), employees.get(employees.size() - 1).getId());
  }

  @Test
  void testFindByCriteria() {
    final var employee1 = Employee.builder()
        .name("John")
        .lastName("Smith")
        .build();
    final var employee2 = Employee.builder()
        .name("John")
        .lastName("Doe")
        .build();
    this.employeeRepository.saveAll(List.of(employee1, employee2));
    assertEquals(2, this.employeeRepository.findAll(EmployeeSpecifications.search("John", null, null)).size());
    assertEquals(1, this.employeeRepository.findAll(EmployeeSpecifications.search("John", "Smith", null)).size());
  }
}
