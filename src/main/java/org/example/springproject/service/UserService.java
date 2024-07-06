package org.example.springproject.service;

import org.example.springproject.dto.user.UserRegistrationRequestDto;
import org.example.springproject.dto.user.UserResponseDto;
import org.example.springproject.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;
}
