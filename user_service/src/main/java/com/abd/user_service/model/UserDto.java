package com.abd.user_service.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
public class UserDto {
    private final long id;
    private final String name;
    private final List<Notification> notifications;
}
