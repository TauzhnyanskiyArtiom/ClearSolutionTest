package com.demo.users.service;

import com.demo.users.api.dto.SearchUsersDto;
import com.demo.users.api.dto.UserCreateReplaceDto;
import com.demo.users.api.dto.UserEditDto;
import com.demo.users.api.dto.UserReadDto;
import com.demo.users.api.mapper.UserDtoMapper;
import com.demo.users.api.mapper.UserEditMapper;
import com.demo.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private Map<String, User> users;

    private final UserDtoMapper userDtoMapper;

    private final UserEditMapper userEditMapper;

    @PostConstruct
    private void init() {
        this.users = new ConcurrentHashMap<>();
    }


    public Optional<User> findById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    public UserReadDto create(UserCreateReplaceDto userDto) {
        return Optional.of(userDto)
                .map(userDtoMapper::toModel)
                .map(user -> {
                    String id = UUID.randomUUID().toString();
                    users.put(id, user);
                    UserReadDto userReadDto = userDtoMapper.toReadDto(user);
                    userReadDto.setId(id);
                    return userReadDto;
                })
                .orElseThrow();
    }

    public Optional<UserReadDto> replace(String id, UserCreateReplaceDto userDto) {
        return findById(id)
                .map(user -> {
                    User newUser = userDtoMapper.toModel(userDto);
                    users.put(id, newUser);
                    return newUser;
                }).map(user -> {
                    UserReadDto userReadDto = userDtoMapper.toReadDto(user);
                    userReadDto.setId(id);
                    return userReadDto;
                });
    }

    public Optional<UserReadDto> edit(String id, UserEditDto userDto) {
        return findById(id)
                .map(user -> {
                    User newUser = userEditMapper.toModel(userDto, user);
                    users.put(id, newUser);
                    return newUser;
                }).map(user -> {
                    UserReadDto userReadDto = userDtoMapper.toReadDto(user);
                    userReadDto.setId(id);
                    return userReadDto;
                });
    }


    public boolean delete(String id) {
        return findById(id)
                .map(user -> {
                    users.remove(id);
                    return true;
                }).orElse(false);
    }

    public List<UserReadDto> getUsersByBirthDate(SearchUsersDto searchUsersDto) {
        return users.entrySet().stream().filter(entry -> {
                    LocalDate birthDate = entry.getValue().getBirthDate();
                    return birthDate.isBefore(searchUsersDto.getTo()) && birthDate.isAfter(searchUsersDto.getFrom());
                }).map(Map.Entry::getValue)
                .map(userDtoMapper::toReadDto)
                .collect(Collectors.toList());
    }
}
