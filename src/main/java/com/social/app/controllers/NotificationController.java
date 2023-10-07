package com.social.app.controllers;

import com.social.app.models.Notification;
import com.social.app.payload.response.ApiResponse;
import com.social.app.payload.response.ApiResponseWithData;
import com.social.app.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationsService;

    @Autowired
    public NotificationController(NotificationService notificationsService) {
        this.notificationsService = notificationsService;
    }

    
    @PostMapping
    public Notification createNotification(@RequestBody Notification notifications) {
        return notificationsService.saveNotification(notifications);
    }

    
    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotification() {
        return ResponseEntity.ok(notificationsService.getAllNotification());
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        return new ResponseEntity<>(notificationsService.getNotificationById(id),HttpStatus.OK);
    }

    
    @PutMapping("/{id}")
    public Notification updateNotification(@PathVariable Long id, @RequestBody Notification updatedNotification) {
        Notification existingNotification = notificationsService.getNotificationById(id);
        updatedNotification.setId(existingNotification.getId());
        return notificationsService.saveNotification(updatedNotification);
    }

    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable Long id) {
        notificationsService.deleteNotification(id);
    }
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<?> getNotificationsByUser(@PathVariable String userId) {
        try {
            List<Notification> notifications = notificationsService.getNotificationsByUser(userId);
            return ResponseEntity.ok(new ApiResponseWithData<List<Notification>>(true, "Get notifications", notifications));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, e.getMessage()));
        }
    }
}
