package com.abd.user_service.service;

import com.abd.user_service.entity.User;
import com.abd.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public List<User> fetchUsers() {
        return userRepository.findAll();
    }
}
