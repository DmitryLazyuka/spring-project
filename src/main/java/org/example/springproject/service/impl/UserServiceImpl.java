package org.example.springproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.springproject.dto.user.UserRegistrationRequestDto;
import org.example.springproject.dto.user.UserResponseDto;
import org.example.springproject.exception.RegistrationException;
import org.example.springproject.mapper.UserMapper;
import org.example.springproject.model.User;
import org.example.springproject.repository.user.UserRepository;
import org.example.springproject.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("Can't register the user");
        }
        User user = userMapper.toUser(requestDto);
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponseDto(savedUser);
    }
}
