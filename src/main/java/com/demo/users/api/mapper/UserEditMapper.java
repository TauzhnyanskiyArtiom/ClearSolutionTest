package com.demo.users.api.mapper;

import com.demo.users.api.dto.UserEditDto;
import com.demo.users.model.User;
import org.springframework.stereotype.Component;

import static org.springframework.util.StringUtils.hasText;

@Component
public class UserEditMapper {

    public User toModel(UserEditDto userEditDto, User user) {

        if(hasText(userEditDto.getEmail()))
            user.setEmail(userEditDto.getEmail());

        if(hasText(userEditDto.getFirstname()))
            user.setFirstname(userEditDto.getFirstname());

        if(hasText(userEditDto.getLastname()))
            user.setLastname(userEditDto.getLastname());

        if(userEditDto.getBirthDate() != null)
            user.setBirthDate(userEditDto.getBirthDate());

        if(hasText(userEditDto.getPhoneNumber()))
            user.setPhoneNumber(userEditDto.getPhoneNumber());

        if(hasText(userEditDto.getAddress()))
            user.setAddress(userEditDto.getAddress());

        return user;
    }
}
