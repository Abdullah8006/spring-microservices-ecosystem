package com.abd.notification_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class NotificationDto {
    private long id;
    private String message;
    private String recipient;
    private boolean read;
    private LocalDate date;
}
