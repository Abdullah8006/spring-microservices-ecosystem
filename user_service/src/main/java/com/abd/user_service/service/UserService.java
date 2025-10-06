package com.abd.user_service.service;

import com.abd.user_service.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    public List<User> fetchUsers() {
<<<<<<< Updated upstream

        User user = new User();
        user.setId(123L);
        user.setName("John Cena");
=======
        User user = new User();
        user.setName("John Cena");
        user.setId(123L);
>>>>>>> Stashed changes

        return List.of(user);
    }
}
