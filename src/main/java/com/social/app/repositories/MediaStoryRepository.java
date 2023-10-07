package com.social.app.repositories;

import com.social.app.models.MediaStory;
import com.social.app.models.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaStoryRepository extends JpaRepository<MediaStory, Long> {
    List<MediaStory> findByStory(Story story);
}
