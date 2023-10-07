package com.social.app.repositories;

import com.social.app.models.Friend;
import com.social.app.models.Person;
import com.social.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    Friend findByUserAndFriend(User user, User friend);
}