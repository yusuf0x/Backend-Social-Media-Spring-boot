package com.social.app.repositories;

import com.social.app.models.Follower;
import com.social.app.models.Person;
import com.social.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {
    Follower findByUserAndFollower(User user, Person follower);
}
