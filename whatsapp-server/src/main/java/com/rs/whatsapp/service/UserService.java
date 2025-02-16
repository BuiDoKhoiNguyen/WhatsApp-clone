package com.rs.whatsapp.service;

import com.rs.whatsapp.mapper.UserMapper;
import com.rs.whatsapp.payload.response.UserResponse;
import com.rs.whatsapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponse> getAllUsersExceptSelf(Authentication authentication) {
        return userRepository.findAllExceptCurrent(authentication.getName())
                .stream()
                .map(userMapper::toUserResponse)
                .collect(java.util.stream.Collectors.toList());
    }

}
