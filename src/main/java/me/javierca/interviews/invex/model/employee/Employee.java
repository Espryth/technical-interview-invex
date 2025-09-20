package me.javierca.interviews.invex.model.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.javierca.interviews.invex.constants.Patterns;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@DynamicUpdate
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String lastName;
  private String secondLastName;
  private int age;
  private Gender gender;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Patterns.BIRTH_DATE_PATTERN)
  private LocalDate birthDate;

  private Position position;

  private Boolean active;

  @JsonIgnore
  private boolean deleted;

  @CreationTimestamp
  private LocalDateTime createdAt;

}