package com.social.app.services;

import com.social.app.exceptions.StoryNotFoundException;
import com.social.app.models.MediaStory;
import com.social.app.models.Story;
import com.social.app.models.User;
import com.social.app.repositories.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class StoryService {

    private final StoryRepository storiesRepository;
    private final MediaStoryService mediaStoryService;

    private final UserService userService;
    private final FileService fileService;

    @Autowired
    public StoryService(StoryRepository storiesRepository, MediaStoryService mediaStoryService, UserService userService, FileService fileService) {
        this.storiesRepository = storiesRepository;
        this.mediaStoryService = mediaStoryService;
        this.userService = userService;
        this.fileService = fileService;
    }

    public List<Story> getAllStory() {
        return storiesRepository.findAll();
    }

    public Story getStoryById(Long uidStory) {
        return storiesRepository.findById(uidStory).orElseThrow(
                () -> new StoryNotFoundException("Story Not FOund with id = "+uidStory)
        );
    }
    public Story saveStory(Story stories) {
        return storiesRepository.save(stories);
    }
    public void deleteStory(Long uidStory) {
        Story story = getStoryById(uidStory);
        storiesRepository.delete(story);
    }

    public boolean checkStoryExists(Long userId) {
        User user = userService.getUserById(userId);
        Story existingStory = storiesRepository.findByUser(user);
        return existingStory != null;
    }
    public void addMediaToExistingStory(Long userId, MultipartFile file) throws IOException {
        User user = userService.getUserById(userId);
        Story existingStory = storiesRepository.findByUser(user);
        String filename = fileService.uploadImage("/stories",file);
        MediaStory mediaStory = new MediaStory(
                null,
                filename,
                new Date(),
                existingStory
        );
        mediaStoryService.saveMediaStory(mediaStory);
    }
    public void addNewStory(Long userId, MultipartFile file) throws IOException {
        User user = userService.getUserById(userId);
        Story story = new Story(null,user);
        story = saveStory(story);
        String filename = fileService.uploadImage("/stories",file);
        MediaStory mediaStory = new MediaStory(
                null,
                filename,
                new Date(),
                story
        );
        mediaStoryService.saveMediaStory(mediaStory);
    }

    public List<MediaStory> getAllStoriesByUser(Long userId) {
        User user = userService.getUserById(userId);
        Story existingStory = storiesRepository.findByUser(user);
        return mediaStoryService.findByStory(existingStory);
    }
}
