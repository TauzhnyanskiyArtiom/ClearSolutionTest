package com.demo.users.api.controller;


import com.demo.users.api.dto.SearchUsersDto;
import com.demo.users.api.dto.UserCreateReplaceDto;
import com.demo.users.api.dto.UserEditDto;
import com.demo.users.api.dto.UserReadDto;
import com.demo.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserRestController {

    private final UserService userService;

    @GetMapping
    public List<UserReadDto> getUsers(@RequestBody @Validated SearchUsersDto searchUsersDto) {
        return userService.getUsersByBirthDate(searchUsersDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto create(@RequestBody @Validated UserCreateReplaceDto userCreateReplaceDto) {
        return userService.create(userCreateReplaceDto);
    }

    @PutMapping("/{id}")
    public UserReadDto replace(@PathVariable("id") String id, @RequestBody @Validated UserCreateReplaceDto userCreateReplaceDto) {
        return userService.replace(id, userCreateReplaceDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}")
    public UserReadDto update(@PathVariable("id") String id, @RequestBody @Validated UserEditDto userEditDto) {
        return userService.edit(id, userEditDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "OK";
    }

}
