package com.abd.user_service.service;

import com.abd.user_service.model.Notification;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final String GET_NOTIFICATIONS_URL = "http://localhost:3000/api/v1/notifications"; // Replace with your actual URL
    private final WebClient webClient;

    @CircuitBreaker(name = "notification_circuit_breaker", fallbackMethod = "getCachedNotificationList")
    public List<Notification> getNotifications() {

        List<Notification> notifications = webClient.get()
                .uri(GET_NOTIFICATIONS_URL)
                .retrieve()
                .bodyToFlux(Notification.class)
                .collectList()
                .block();

        System.out.println("Response from notification service:" + notifications);
        return notifications;
    }

    public List<Notification> getCachedNotificationList(Exception ex) {
        Notification notification = new Notification();
        notification.setDate(LocalDate.now());
        notification.setId(213);
        notification.setMessage("test message");
        notification.setRecipient("Test");
        notification.setRead(false);

        return List.of(notification);
    }

    /**
     * Fallback method called when the circuit breaker is open.
     *
     * @param ex Exception thrown
     * @return Fallback response
     */
    public String fallbackResponse(Exception ex) {
        return "Fallback response: " + ex.getMessage();
    }
}
