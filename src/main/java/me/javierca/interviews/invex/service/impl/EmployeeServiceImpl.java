package me.javierca.interviews.invex.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.javierca.interviews.invex.exception.EmployeeNotFoundException;
import me.javierca.interviews.invex.model.employee.Employee;
import me.javierca.interviews.invex.model.employee.EmployeeDTO;
import me.javierca.interviews.invex.repository.EmployeeRepository;
import me.javierca.interviews.invex.repository.EmployeeSpecifications;
import me.javierca.interviews.invex.service.EmployeeService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;

  @Override
  public @NotNull Employee getEmployee(final long id) {
    log.info("Getting employee with id {}", id);
    return this.employeeRepository.findByIdAndDeletedIsFalse(id)
        .orElseThrow(() -> new EmployeeNotFoundException("with id " + id));
  }

  @Override
  public @NotNull List<Employee> searchEmployees(
      final @Nullable String name,
      final @Nullable String lastName,
      final @Nullable String secondLastName
  ) {
    log.info("Searching employees for name {} and lastName {} and secondLastName {}", name, lastName, secondLastName);
    final var employees = this.employeeRepository.findAll(EmployeeSpecifications.search(name, lastName, secondLastName));
    if (employees.isEmpty()) {
      throw new EmployeeNotFoundException("with the given search criteria");
    }
    return employees;
  }

  @Override
  public @NotNull List<Employee> getAllEmployees() {
    log.info("Getting all employees");
    return this.employeeRepository.findAllByDeletedIsFalse();
  }

  @Override
  public @NotNull List<Employee> createEmployees(final @NotNull List<EmployeeDTO> employeesDTO) {
    log.info("Creating {} employees", employeesDTO.size());
    return this.employeeRepository.saveAll(
        employeesDTO.stream()
            .map(dto -> {
              final var employee = dto.toEntity();
              employee.setActive(true);
              return employee;
            })
            .toList()
    );
  }

  @Override
  @Transactional
  public @NotNull Employee updateEmployee(final long id, final @NotNull EmployeeDTO employeeDTO) {
    log.info("Updating employee with id {}", id);
    final var employee = this.getEmployee(id);
    if (employeeDTO.name() != null) employee.setName(employeeDTO.name());
    if (employeeDTO.lastName() != null) employee.setLastName(employeeDTO.lastName());
    if (employeeDTO.secondLastName() != null) employee.setSecondLastName(employeeDTO.secondLastName());
    if (employeeDTO.age() != null) employee.setAge(employeeDTO.age());
    if (employeeDTO.gender() != null) employee.setGender(employeeDTO.gender());
    if (employeeDTO.birthDate() != null) employee.setBirthDate(employeeDTO.birthDate());
    if (employeeDTO.position() != null) employee.setPosition(employeeDTO.position());
    if (employeeDTO.active() != null) employee.setActive(employeeDTO.active());
    log.info("Employee with id {} updated", id);
    return employee;
  }

  @Override
  @Transactional
  public @NotNull Employee deleteEmployee(final long id) {
    log.info("Deleting employee with id {}", id);
    final var employee = this.getEmployee(id);
    employee.setDeleted(true);
    log.info("Employee with id {} deleted", id);
    return employee;
  }
}
