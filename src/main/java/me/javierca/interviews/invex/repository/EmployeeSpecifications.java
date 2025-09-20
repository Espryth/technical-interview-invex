package me.javierca.interviews.invex.repository;

import me.javierca.interviews.invex.model.employee.Employee;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

public final class EmployeeSpecifications {

  public static Specification<Employee> search(
      final @Nullable String name,
      final @Nullable String lastName,
      final @Nullable String secondLastName
  ) {
    return (root, query, criteriaBuilder) -> {
      var predicates = criteriaBuilder.not(criteriaBuilder.equal(root.get("deleted"), true));
      if (name != null) {
        predicates = criteriaBuilder.and(predicates, criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
      }
      if (lastName != null) {
        predicates = criteriaBuilder.and(predicates, criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%"));
      }
      if (secondLastName != null) {
        predicates = criteriaBuilder.and(predicates, criteriaBuilder.like(criteriaBuilder.lower(root.get("secondLastName")), "%" + secondLastName.toLowerCase() + "%"));
      }
      return predicates;
    };
  }
}
