package com.abd.user_service.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class Notification {
    private long id;
    private String message;
    private String recipient;
    private boolean read;
    private LocalDate date;
}
