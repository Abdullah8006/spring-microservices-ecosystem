package com.abd.notification_service.controller;

import com.abd.notification_service.dto.NotificationDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @GetMapping
    public List<NotificationDto> getNofitications() {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setId(123L);
        notificationDto.setRead(false);
        notificationDto.setDate(LocalDate.now());
        notificationDto.setRecipient("Abd");
        notificationDto.setMessage("A user has added comment");

        return List.of(notificationDto);
    }
}
