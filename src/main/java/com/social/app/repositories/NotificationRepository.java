package com.social.app.repositories;

import com.social.app.models.Notification;
import com.social.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Notification findByUserAndFollower(User user,User follower);
}
