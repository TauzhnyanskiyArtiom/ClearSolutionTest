package com.demo.users.api.mapper;

import com.demo.users.api.dto.UserCreateReplaceDto;
import com.demo.users.api.dto.UserReadDto;
import com.demo.users.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserDtoMapper {
    User toModel(UserCreateReplaceDto user);
    UserReadDto toReadDto(User user);
}
