package com.nexbank.api.service;

import com.nexbank.api.controller.request.CreateUserRequest;
import com.nexbank.api.dto.UserDTO;
import com.nexbank.api.infrastructure.security.JwtTokenProvider;
import com.nexbank.api.infrastructure.security.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;


    public UserDTO register(CreateUserRequest request){
        return userService.registerUser(request);
    }

}
