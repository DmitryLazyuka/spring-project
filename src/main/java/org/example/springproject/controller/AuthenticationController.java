package org.example.springproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springproject.dto.user.UserRegistrationRequestDto;
import org.example.springproject.dto.user.UserResponseDto;
import org.example.springproject.exception.RegistrationException;
import org.example.springproject.repository.user.UserRepository;
import org.example.springproject.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authorization controller", description = "Endpoint for user registration")
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/registration")
    @Operation(summary = "Register new User")
    public UserResponseDto register(
            @RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }
}
