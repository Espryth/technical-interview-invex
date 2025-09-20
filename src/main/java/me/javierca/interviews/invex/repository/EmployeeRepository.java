package me.javierca.interviews.invex.repository;

import me.javierca.interviews.invex.model.employee.Employee;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

  Optional<Employee> findByIdAndDeletedIsFalse(final long id);

  List<Employee> findAllByDeletedIsFalse();
}
