package me.javierca.interviews.invex.model.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import me.javierca.interviews.invex.constants.Patterns;
import me.javierca.interviews.invex.util.validation.ValidationGroups;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Builder
public record EmployeeDTO(
    @NotNull(groups = ValidationGroups.Full.class)
    @Pattern(regexp = Patterns.NOT_EMPTY, groups = {ValidationGroups.Full.class, ValidationGroups.Partial.class})
    String name,
    @NotNull(groups = ValidationGroups.Full.class)
    @Pattern(regexp = Patterns.NOT_EMPTY, groups = {ValidationGroups.Full.class, ValidationGroups.Partial.class})
    String lastName,
    @NotNull(groups = ValidationGroups.Full.class)
    @Pattern(regexp = Patterns.NOT_EMPTY, groups = {ValidationGroups.Full.class, ValidationGroups.Partial.class})
    String secondLastName,
    @NotNull(groups = ValidationGroups.Full.class)
    @Range(min = 18, max = 80, groups = {ValidationGroups.Full.class, ValidationGroups.Partial.class})
    Integer age,
    @NotNull(groups = ValidationGroups.Full.class)
    Gender gender,
    @NotNull(groups = ValidationGroups.Full.class)
    @Past(groups = {ValidationGroups.Full.class, ValidationGroups.Partial.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Patterns.BIRTH_DATE_PATTERN)
    LocalDate birthDate,
    @NotNull(groups = ValidationGroups.Full.class)
    Position position,
    Boolean active
) {

    public Employee toEntity() {
        return Employee.builder()
            .name(this.name)
            .lastName(this.lastName)
            .secondLastName(this.secondLastName)
            .age(this.age)
            .gender(this.gender)
            .birthDate(this.birthDate)
            .position(this.position)
            .active(this.active != null ? this.active : true)
            .build();
    }
}
