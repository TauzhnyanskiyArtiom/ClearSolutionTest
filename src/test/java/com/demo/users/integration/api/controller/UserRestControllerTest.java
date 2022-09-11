package com.demo.users.integration.api.controller;

import com.demo.users.api.dto.SearchUsersDto;
import com.demo.users.api.dto.UserCreateReplaceDto;
import com.demo.users.api.dto.UserReadDto;
import com.demo.users.integration.adapter.LocalDateAdapter;
import com.demo.users.integration.adapter.LocalDateDesAdapter;
import com.demo.users.integration.annotation.IT;
import com.demo.users.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IT
@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserRestControllerTest {

    private final MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private static Gson gson;

    private UserCreateReplaceDto userTestDto;

    @BeforeAll
    static void setUp() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateDesAdapter())
                .create();
    }

    @BeforeEach
    void setUser(){
        this.userTestDto =  new UserCreateReplaceDto("test@gmail.com",
                "Test", "TestTest", LocalDate.of(2002, 1, 21), "Test test", "+38093243432");

    }

    @Test
    void createUser_when_validRequest() throws Exception {

        String dtoJson = gson.toJson(userTestDto);
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson)

                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(userTestDto.getEmail()))
                .andExpect(jsonPath("$.firstname").value(userTestDto.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(userTestDto.getLastname()))
                .andExpect(jsonPath("$.address").value(userTestDto.getAddress()))
                .andExpect(jsonPath("$.phoneNumber").value(userTestDto.getPhoneNumber()))
                .andReturn();

       UserReadDto resultDto = gson.fromJson(mvcResult.getResponse().getContentAsString(), UserReadDto.class);

       userService.delete(resultDto.getId());

    }

    @Test
    void createUser_when_invalidEmail() throws Exception {

        userTestDto.setEmail("test");
        String dtoJson = gson.toJson(userTestDto);
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson)

                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void createUser_when_invalidBirthDate() throws Exception {
        userTestDto.setBirthDate(LocalDate.of(2024,1,1));
        String dtoJson = gson.toJson(userTestDto);
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson)

                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void replaceUser_when_validRequest() throws Exception {

        UserReadDto resultDto = userService.create(userTestDto);

        UserCreateReplaceDto newDto = new UserCreateReplaceDto("test2@gmail.com",
                "FirstName", "LastName", LocalDate.of(1990, 2, 22), null, null);

        String dtoJson = gson.toJson(newDto);
        mockMvc.perform(put("/api/v1/users/" + resultDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson)

                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(newDto.getEmail()))
                .andExpect(jsonPath("$.firstname").value(newDto.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(newDto.getLastname()))
                .andExpect(jsonPath("$.address").value(newDto.getAddress()))
                .andExpect(jsonPath("$.phoneNumber").value(newDto.getPhoneNumber()))
                .andReturn();

        userService.delete(resultDto.getId());

    }

    @Test
    void replaceUser_when_invalidRequest() throws Exception {

        UserCreateReplaceDto newDto = new UserCreateReplaceDto("test2@gmail.com",
                "FirstName", "LastName", LocalDate.of(1990, 2, 22), null, null);

        String dtoJson = gson.toJson(newDto);
        mockMvc.perform(put("/api/v1/users/" + "testId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson)

                )
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));


    }

    @Test
    void editUser_when_validRequest() throws Exception {

        UserReadDto resultDto = userService.create(userTestDto);

        UserCreateReplaceDto newDto = new UserCreateReplaceDto();
        newDto.setEmail("testEmail@gmail.com");

        String dtoJson = gson.toJson(newDto);
        mockMvc.perform(patch("/api/v1/users/" + resultDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson)

                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(newDto.getEmail()))
                .andExpect(jsonPath("$.firstname").value(userTestDto.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(userTestDto.getLastname()))
                .andExpect(jsonPath("$.address").value(userTestDto.getAddress()))
                .andExpect(jsonPath("$.phoneNumber").value(userTestDto.getPhoneNumber()))
                .andReturn();

        userService.delete(resultDto.getId());

    }

    @Test
    void editUser_when_invalidRequest() throws Exception {

        UserCreateReplaceDto newDto = new UserCreateReplaceDto();
        newDto.setEmail("testEmail@gmail.com");

        String dtoJson = gson.toJson(newDto);
        mockMvc.perform(patch("/api/v1/users/" + "testId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson)

                )
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));

    }

    @Test
    void deleteUser_when_validRequest() throws Exception {

        UserReadDto resultDto = userService.create(userTestDto);

        mockMvc.perform(delete("/api/v1/users/" + resultDto.getId()))
                .andExpect(status().isOk());

    }

    @Test
    void deleteUser_when_invalidRequest() throws Exception {

        mockMvc.perform(delete("/api/v1/users/" + "testId"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));


    }

    @Test
    void searchUsers_when_validRequest() throws Exception {

        userService.create(userTestDto);
        UserReadDto userReadDto = userService.create(new UserCreateReplaceDto("test2@gmail.com",
                "Test2", "TestTest2", LocalDate.of(2000, 1, 1), null, null));
        UserReadDto userReadDto1 = userService.create(new UserCreateReplaceDto("test3@gmail.com",
                "Test3", "TestTest3", LocalDate.of(1998, 1, 1), "Test", null));


        SearchUsersDto searchUsersDto = new SearchUsersDto(LocalDate.of(1999, 1, 1), LocalDate.of(2003, 1, 1));
        String dtoJson = gson.toJson(searchUsersDto);
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson)

                )
                .andExpect(status().isOk())
                .andReturn();

        List<UserReadDto> list = gson.fromJson(mvcResult.getResponse().getContentAsString(), new TypeReference<List<UserReadDto>>() {}.getType());

        assertThat(list).hasSize(2);
    }

    @Test
    void searchUsers_when_invalidRequest() throws Exception {

        SearchUsersDto searchUsersDto = new SearchUsersDto(LocalDate.of(2002, 1, 1), LocalDate.of(2001, 1, 1));
        String dtoJson = gson.toJson(searchUsersDto);
        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson)

                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }





}