package me.javierca.interviews.invex.service;

import me.javierca.interviews.invex.exception.EmployeeNotFoundException;
import me.javierca.interviews.invex.model.employee.Employee;
import me.javierca.interviews.invex.model.employee.EmployeeDTO;
import me.javierca.interviews.invex.repository.EmployeeRepository;
import me.javierca.interviews.invex.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

  @Mock
  private EmployeeRepository employeeRepository;

  @InjectMocks
  private EmployeeServiceImpl employeeService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetEmployee() {
    when(this.employeeRepository.findByIdAndDeletedIsFalse(anyLong())).thenReturn(Optional.of(new Employee()));
    assertNotNull(this.employeeService.getEmployee(1L));
    when(employeeRepository.findByIdAndDeletedIsFalse(anyLong())).thenReturn(Optional.empty());
    assertThrows(EmployeeNotFoundException.class, () -> this.employeeService.getEmployee(1L));
  }

  @Test
  void testSearchEmployees() {
    when(this.employeeRepository.findAll(any(Specification.class))).thenReturn(List.of(new Employee()));
    assertNotNull(this.employeeService.searchEmployees("John", null, null));
    when(employeeRepository.findAll(any(Specification.class))).thenReturn(java.util.List.of());
    assertThrows(EmployeeNotFoundException.class, () -> this.employeeService.searchEmployees("John", null, null));
  }

  @Test
  void testGetAllEmployees() {
    when(this.employeeRepository.findAllByDeletedIsFalse()).thenReturn(List.of(new Employee()));
    assertDoesNotThrow(() -> this.employeeService.getAllEmployees());
    assertNotNull(this.employeeService.getAllEmployees());
  }

  @Test
  void testCreateEmployees() {
    when(this.employeeRepository.saveAll(any())).thenReturn(List.of(new Employee()));
    assertNotNull(this.employeeService.createEmployees(List.of(EmployeeDTO.builder().age(1).build())).get(0));
  }

  @Test
  void testUpdateEmployee() {
    when(this.employeeRepository.findByIdAndDeletedIsFalse(anyLong())).thenReturn(Optional.of(new Employee()));
    final var employee = assertDoesNotThrow(() -> this.employeeService.updateEmployee(1L, EmployeeDTO.builder().name("John").build()));
    assertEquals("John", employee.getName());
  }

  @Test
  void testDeleteEmployee() {
    when(this.employeeRepository.findByIdAndDeletedIsFalse(anyLong())).thenReturn(Optional.of(new Employee()));
    final var employee = assertDoesNotThrow(() -> this.employeeService.deleteEmployee(1L));
    assertTrue(employee.isDeleted());
  }
}
