package com.foodApp.service.impl;

import com.foodApp.dto.request.UserRequestDTO;
import com.foodApp.entity.User;
import com.foodApp.exception.DuplicatedException;
import com.foodApp.exception.ResourceNotFoundException;
import com.foodApp.repository.UserRepository;
import com.foodApp.service.UserService;
import com.foodApp.util.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public Long create(UserRequestDTO request){
        if (userRepository.existsByUsername(request.getUsername())){
            throw new DuplicatedException("Username already exists");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setGender(request.getGender());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setStatus(request.getStatus());
        user.setDob(request.getDob());
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Override
    public void updateStatus(long userId, String status){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setStatus(status);
        userRepository.save(user);
    }

    @Override
    public void verifyUser(String username, Integer verificationCode){
        if (!userRepository.existsByUsernameAndVerificationCode(username, verificationCode)){
            throw new ResourceNotFoundException("Invalid verification code");
        }
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setStatus(UserStatus.ACTIVE.name());
        userRepository.save(user);
    }
}
