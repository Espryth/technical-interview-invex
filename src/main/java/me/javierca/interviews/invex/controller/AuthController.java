package me.javierca.interviews.invex.controller;

import lombok.AllArgsConstructor;
import me.javierca.interviews.invex.constants.Responses;
import me.javierca.interviews.invex.model.response.Response;
import me.javierca.interviews.invex.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

  private final TokenService tokenService;

  @GetMapping("/auth")
  ResponseEntity<Response> generateToken() {
    return Responses.ok(this.tokenService.generateToken().getToken());
  }
}
