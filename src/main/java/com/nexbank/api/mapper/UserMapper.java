package com.nexbank.api.mapper;

import com.nexbank.api.controller.request.CreateUserRequest;
import com.nexbank.api.domain.User;
import com.nexbank.api.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    User toDomain(CreateUserRequest request);

}
