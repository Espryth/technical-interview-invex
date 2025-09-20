package me.javierca.interviews.invex.service;

import me.javierca.interviews.invex.model.employee.Employee;
import me.javierca.interviews.invex.model.employee.EmployeeDTO;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface EmployeeService {

  @NotNull Employee getEmployee(final long id);

  @NotNull List<Employee> searchEmployees(
      final @Nullable String name,
      final @Nullable String lastName,
      final @Nullable String secondLastName
  );

  @NotNull List<Employee> getAllEmployees();

  @NotNull List<Employee> createEmployees(final @NotNull List<EmployeeDTO> employeesDTO);

  @NotNull Employee updateEmployee(final long id, final @NotNull EmployeeDTO employeeDTO);

  @NotNull Employee deleteEmployee(final long id);

}
