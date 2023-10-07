package com.social.app.services;

import com.social.app.models.MediaStory;
import com.social.app.models.Story;
import com.social.app.repositories.MediaStoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MediaStoryService {

    private final MediaStoryRepository mediaStoryRepository;

    @Autowired
    public MediaStoryService(MediaStoryRepository mediaStoryRepository) {
        this.mediaStoryRepository = mediaStoryRepository;
    }

    public List<MediaStory> getAllMediaStories() {
        return mediaStoryRepository.findAll();
    }
    public List<MediaStory> findByStory(Story story){
        return mediaStoryRepository.findByStory(story);
    }

    public Optional<MediaStory> getMediaStoryById(Long uidMediaStory) {
        return mediaStoryRepository.findById(uidMediaStory);
    }

    public MediaStory saveMediaStory(MediaStory mediaStory) {
        return mediaStoryRepository.save(mediaStory);
    }

    public void deleteMediaStory(Long uidMediaStory) {
        mediaStoryRepository.deleteById(uidMediaStory);
    }

}
