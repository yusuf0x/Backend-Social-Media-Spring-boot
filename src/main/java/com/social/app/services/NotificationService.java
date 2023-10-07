package com.social.app.services;

import com.social.app.exceptions.NotificationNotFoundException;
import com.social.app.models.Notification;
import com.social.app.models.User;
import com.social.app.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationsRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationsRepository) {
        this.notificationsRepository = notificationsRepository;
    }

    public List<Notification> getAllNotification() {
        return notificationsRepository.findAll();
    }

    public Notification getNotificationById(Long notificationId) {
        return notificationsRepository.findById(notificationId).orElseThrow(
                () -> new NotificationNotFoundException("Notification Not Found with id = "+notificationId)
        );
    }

    public Notification saveNotification(Notification notifications) {
        return notificationsRepository.save(notifications);
    }

    public void deleteNotification(Long notificationId) {
        Notification notification = getNotificationById(notificationId);
        notificationsRepository.delete(notification);
    }

    public List<Notification> getNotificationsByUser(String userId) {
        return notificationsRepository.findAll();
    }
    public  Notification findByUserAndFollower(User user , User follower){
        return notificationsRepository.findByUserAndFollower(user,follower);
    }
}
