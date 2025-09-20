package me.javierca.interviews.invex.controller;

import me.javierca.interviews.invex.model.employee.EmployeeDTO;
import me.javierca.interviews.invex.repository.EmployeeRepository;
import me.javierca.interviews.invex.service.EmployeeService;
import me.javierca.interviews.invex.service.TokenService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class EmployeeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private EmployeeRepository employeeRepository;

  private static String token;

  @BeforeAll
  static void init(@Autowired TokenService tokenService) {
    token = tokenService.generateToken().getToken();
  }

  @Test
  void testGetById200() throws Exception {
    this.employeeService.createEmployees(List.of(
        EmployeeDTO.builder()
            .name("Javier")
            .lastName("Candelario")
            .age(21)
            .build()
    ));
    this.mockMvc.perform(
        get("/employees/" + 1)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result.name").value("Javier"))
        .andExpect(jsonPath("$.result.lastName").value("Candelario"));
  }

  @Test
  void testGetById404() throws Exception {
    this.mockMvc.perform(
        get("/employees/" + 99)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound());
  }

  @Test
  void testGetById401() throws Exception {
    this.mockMvc.perform(
        get("/employees/" + 1)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isUnauthorized());
  }

  @Test
  void testCreate201() throws Exception {
    System.out.println("Employee count before: " + this.employeeRepository.count());
    this.mockMvc.perform(
            post("/employees")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "employees": [
                        {
                          "name": "Javier",
                          "lastName": "Candelario",
                          "secondLastName": "Arcos",
                          "age": "21",
                          "gender": "MALE",
                          "birthDate": "05-02-2004",
                          "position": "INTERN"
                        }
                      ]
                    }
                    """
                )
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.result.[0].id").exists());
  }

  @Test
  void testCreate400() throws Exception {
    this.mockMvc.perform(
        post("/employees")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                {
                  "employees": [
                    {
                      "name": "Javier",
                      "lastName": "Candelario",
                      "age": 21
                    },
                    {
                      "name": "John",
                      "lastName": "Doe",
                      "age": 30
                    }             
                  ]
                }

                """
              )
          )
          .andExpect(status().isBadRequest());
  }

  @Test
  void testUpdate200() throws Exception {
    this.employeeService.createEmployees(List.of(
        EmployeeDTO.builder()
            .name("Javier")
            .lastName("Candelario")
            .age(21)
            .build()
    ));
    this.mockMvc.perform(
            put("/employees/" + 1)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "age": 30
                    }
                    """
                )
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result.age").value("30"));
  }


}
