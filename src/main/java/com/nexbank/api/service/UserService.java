package com.nexbank.api.service;

import com.nexbank.api.controller.request.CreateUserRequest;
import com.nexbank.api.domain.User;
import com.nexbank.api.dto.UserDTO;
import com.nexbank.api.exception.DuplicateUserException;
import com.nexbank.api.exception.UserNotFoundException;
import com.nexbank.api.mapper.UserMapper;
import com.nexbank.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public UserDTO registerUser(CreateUserRequest request){

        if (repository.existsByEmail(request.getEmail())){
            throw new DuplicateUserException(request.getEmail());
        }

        User.UserBuilder userBuilder = User.builder();
        userBuilder.firstName(request.getFirstName());
        userBuilder.lastName(request.getLastName());
        userBuilder.email(request.getEmail());
        userBuilder.phoneNumber(request.getPhoneNumber());
        userBuilder.password(request.getPassword());
        userBuilder.birthDate(request.getBirthDate());
        userBuilder.createdAt(LocalDateTime.now());

        User user = userBuilder.build();

        User savedUser = repository.save(user);
        return mapper.toDTO(savedUser);

    }

    public UserDTO getUserById(Long id){
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return mapper.toDTO(user);
    }

    public List<UserDTO> getAllUsers(){
        return repository.findAll()
                .stream().map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long id){
        repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        repository.deleteById(id);
    }


}
