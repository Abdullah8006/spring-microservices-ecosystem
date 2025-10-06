package com.abd.user_service.controller;

import com.abd.user_service.model.Notification;
import com.abd.user_service.model.UserDto;
import com.abd.user_service.service.NotificationService;
import com.abd.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final NotificationService notificationService;

    @GetMapping
    public List<UserDto> findUser() {
        List<Notification> userNotifications = notificationService.getNotifications();
        System.out.println("Fetched all notifications");

        return userService.fetchUsers()
                .stream()
                .map(user -> new UserDto(user.getId(), user.getName(), userNotifications))
                .toList();
    }
}
