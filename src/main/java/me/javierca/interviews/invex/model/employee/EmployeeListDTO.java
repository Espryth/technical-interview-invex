package me.javierca.interviews.invex.model.employee;

import javax.validation.Valid;
import java.util.List;

public record EmployeeListDTO(
    @Valid List<EmployeeDTO> employees
) {
}
