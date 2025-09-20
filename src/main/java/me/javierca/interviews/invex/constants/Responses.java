package me.javierca.interviews.invex.constants;

import me.javierca.interviews.invex.model.response.Response;
import org.springframework.http.ResponseEntity;

public final class Responses {

  public static final ResponseEntity<Response> NOT_FOUND = ResponseEntity.status(404).body(Response.of("Not found"));
  public static final ResponseEntity<Response> INTERNAL_ERROR = ResponseEntity.status(500).body(Response.of("Internal server error"));

  public static ResponseEntity<Response> ok(final Object result) {
    return ResponseEntity.ok(Response.of("Success", result));
  }

  public static ResponseEntity<Response> created(final Object result) {
    return ResponseEntity.status(201).body(Response.of("Created", result));
  }

  public static ResponseEntity<Response> badRequest(final String message) {
    return ResponseEntity.status(400).body(Response.of(message));
  }
}
