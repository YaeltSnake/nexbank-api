package com.nexbank.api.mapper;

import com.nexbank.api.domain.User;
import com.nexbank.api.dto.UserDTO;
import com.nexbank.api.enums.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", expression = "java(mapRole(user.getRole()))")
    @Mapping(target = "status", expression = "java(user.getStatus().name())")
    UserDTO toDTO(User user);

    default String mapRole(UserRole role) {
        return role.name().replace("ROLE_", "");
    }
}
