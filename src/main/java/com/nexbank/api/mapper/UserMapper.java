package com.nexbank.api.mapper;

import com.nexbank.api.controller.request.CreateUserRequest;
import com.nexbank.api.domain.User;
import com.nexbank.api.dto.UserDTO;
import com.nexbank.api.enums.UserRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    default String mapRole(UserRole role) {
        return role.name().replace("ROLE_", "");
    }
}
