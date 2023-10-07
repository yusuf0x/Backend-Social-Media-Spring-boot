package com.social.app.repositories;

import com.social.app.models.Story;
import com.social.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
    Story findByUser(User user);
}
