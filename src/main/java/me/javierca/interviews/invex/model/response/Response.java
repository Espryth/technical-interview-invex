package me.javierca.interviews.invex.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Response(
    String message,
    Object result
) {

  public static Response of(final @NotNull String message) {
    return new Response(message, null);
  }

  public static Response of(final @NotNull String message, final @NotNull Object result) {
    return new Response(message, result);
  }
}
