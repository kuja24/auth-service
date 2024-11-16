package com.authentication.auth_service.service;


import com.authentication.auth_service.entity.Address;
import com.authentication.auth_service.entity.User;
import com.authentication.auth_service.exception.FoodDeliveryException;
import com.authentication.auth_service.models.AddressDto;
import com.authentication.auth_service.models.CreateUserRequest;
import com.authentication.auth_service.models.UserDto;
import com.authentication.auth_service.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AddressService addressService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.passwordEncoder = passwordEncoder;
    }

    public Long createUser(CreateUserRequest request) {
        Address addr = addressService.saveAddress(request.getAddress());
        User user = userRepository.save(buildUserEntity(addr, request));
        return user.getUserId();
    }

    public User findUserByUserId(Long userId) {
        User user;
        try {
            user = userRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new FoodDeliveryException("User not found with id: " + userId, HttpStatus.NOT_FOUND);
        }
        return user;
    }

    private User buildUserEntity(Address addr, CreateUserRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        return User.builder()
                .address(addr)
                .passwordHash(encodedPassword)
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .role(request.getRole().name())
                .createdAt(new Date())
                .build();
    }

    public UserDto getUserDtoFromUser(User user) {
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .address(AddressDto.builder()
                        .addressLine(user.getAddress().getAddressLine())
                        .state(user.getAddress().getState())
                        .city(user.getAddress().getCity())
                        .zip(user.getAddress().getZipCode())
                        .build())
                .build();
    }
}