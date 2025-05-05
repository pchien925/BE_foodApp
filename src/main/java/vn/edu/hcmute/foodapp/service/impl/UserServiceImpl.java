package vn.edu.hcmute.foodapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vn.edu.hcmute.foodapp.dto.request.UpdateUserRequest;
import vn.edu.hcmute.foodapp.dto.response.UserResponse;
import vn.edu.hcmute.foodapp.entity.User;
import vn.edu.hcmute.foodapp.exception.ResourceNotFoundException;
import vn.edu.hcmute.foodapp.mapper.UserMapper;
import vn.edu.hcmute.foodapp.repository.UserRepository;
import vn.edu.hcmute.foodapp.service.UserService;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserResponse getUserById(Long id) {
     return UserMapper.INSTANCE.toResponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    @Override
    public UserResponse getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        // Find the user in the repository by username (assuming username is unique)
        return userRepository.findByEmail(username)
                .map(UserMapper.INSTANCE::toResponse)
                .orElseThrow(() -> new RuntimeException("Current user not found"));
    }

    @Override
    public UserResponse updateUser(Long userId, UpdateUserRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (userRepository.existsByPhone(request.getPhone())){
            throw new IllegalArgumentException("Phone number already exists");
        }

        UserMapper.INSTANCE.update(user, request);
        return UserMapper.INSTANCE.toResponse(userRepository.save(user));
    }
}
