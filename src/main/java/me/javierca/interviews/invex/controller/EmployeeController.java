package me.javierca.interviews.invex.controller;

import lombok.AllArgsConstructor;
import me.javierca.interviews.invex.constants.Responses;
import me.javierca.interviews.invex.model.employee.EmployeeDTO;
import me.javierca.interviews.invex.model.employee.EmployeeListDTO;
import me.javierca.interviews.invex.model.response.Response;
import me.javierca.interviews.invex.service.EmployeeService;
import me.javierca.interviews.invex.util.validation.ValidationGroups;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeController {

  private final EmployeeService employeeService;

  @GetMapping("/{id}")
  ResponseEntity<Response> getEmployee(final @Positive @PathVariable long id) {
    return Responses.ok(this.employeeService.getEmployee(id));
  }

  @GetMapping("/search")
  ResponseEntity<Response> searchEmployees(
      final @NotBlank @RequestParam(required = false) String name,
      final @NotBlank @RequestParam(required = false) String lastName,
      final @NotBlank @RequestParam(required = false) String secondLastName
  ) {
    return Responses.ok(this.employeeService.searchEmployees(name, lastName, secondLastName));
  }

  @GetMapping("/all")
  ResponseEntity<Response> getAllEmployees() {
    return Responses.ok(this.employeeService.getAllEmployees());
  }

  @PostMapping
  ResponseEntity<Response> createEmployee(
      final @Validated(ValidationGroups.Full.class) @RequestBody EmployeeListDTO employees
  ) {
    return Responses.created(this.employeeService.createEmployees(employees.employees()));
  }

  @PutMapping("/{id}")
  ResponseEntity<Response> updateEmployee(
      final @Positive @PathVariable long id,
      final @Validated(ValidationGroups.Partial.class) @RequestBody EmployeeDTO employee
  ) {
    return Responses.ok(this.employeeService.updateEmployee(id, employee));
  }

  @DeleteMapping("/{id}")
  ResponseEntity<Response> deleteEmployee(final @Positive @PathVariable long id) {
    return Responses.ok(this.employeeService.deleteEmployee(id));
  }
}
