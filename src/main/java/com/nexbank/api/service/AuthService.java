package com.nexbank.api.service;

import com.nexbank.api.controller.request.CreateUserRequest;
import com.nexbank.api.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public UserDTO register(CreateUserRequest request){
        return userService.registerUser(request);
    }

}
