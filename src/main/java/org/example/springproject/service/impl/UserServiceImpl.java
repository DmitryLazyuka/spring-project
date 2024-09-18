package org.example.springproject.service.impl;

import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.springproject.dto.user.UserRegistrationRequestDto;
import org.example.springproject.dto.user.UserResponseDto;
import org.example.springproject.exception.RegistrationException;
import org.example.springproject.mapper.UserMapper;
import org.example.springproject.model.Role;
import org.example.springproject.model.User;
import org.example.springproject.repository.role.RoleRepository;
import org.example.springproject.repository.user.UserRepository;
import org.example.springproject.service.ShoppingCartService;
import org.example.springproject.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ShoppingCartService shoppingCartService;

    @Override
    @Transactional
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("Can't register the user");
        }
        User user = userMapper.toUser(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRole(Role.RoleName.USER));
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        shoppingCartService.createCart(savedUser);
        return userMapper.toUserResponseDto(savedUser);
    }
}
