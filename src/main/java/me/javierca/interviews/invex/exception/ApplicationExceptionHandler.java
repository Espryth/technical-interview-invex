package me.javierca.interviews.invex.exception;

import lombok.extern.slf4j.Slf4j;
import me.javierca.interviews.invex.Application;
import me.javierca.interviews.invex.constants.Responses;
import me.javierca.interviews.invex.model.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler {

  @ExceptionHandler(Exception.class)
  ResponseEntity<Response> handleAll(final Exception ex) {
    log.error("Unhandled exception in {}", Application.class.getSimpleName(), ex);
    return Responses.INTERNAL_ERROR;
  }

  @ExceptionHandler(EmployeeNotFoundException.class)
  ResponseEntity<Response> handleEmployeeNotFound(final EmployeeNotFoundException ex) {
    log.info("Employee not found {}", ex.getMessage());
    return Responses.NOT_FOUND;
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  ResponseEntity<Response> handleMethodNotSupported(final HttpRequestMethodNotSupportedException ex) {
    log.info("Method not supported {}", ex.getMessage());
    return Responses.badRequest("Method not supported");
  }

  @ExceptionHandler({MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
  ResponseEntity<Response> handleInvalidFormat(final Exception ex) {
    log.warn("Invalid request: {}", ex.getMessage());
    return Responses.badRequest("Invalid request. Check data");
  }


}
