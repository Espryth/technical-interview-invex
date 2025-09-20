package me.javierca.interviews.invex.exception;

public class EmployeeNotFoundException extends RuntimeException {

  public EmployeeNotFoundException(final String message) {
    super(message);
  }
}
