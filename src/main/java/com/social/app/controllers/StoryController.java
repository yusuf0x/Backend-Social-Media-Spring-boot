package com.social.app.controllers;

import com.social.app.models.MediaStory;
import com.social.app.models.Story;
import com.social.app.payload.response.ApiResponse;
import com.social.app.payload.response.ApiResponseWithData;
import com.social.app.services.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stories")
public class StoryController {

    private final StoryService storiesService;

    @Autowired
    public StoryController(StoryService storiesService) {
        this.storiesService = storiesService;
    }

    @PostMapping
    public Story createStory(@RequestBody Story stories) {
        return storiesService.saveStory(stories);
    }
    @PostMapping("/add-new")
    public ResponseEntity<?> addNewStory(@RequestParam("userId") Long userId, @RequestParam("file") MultipartFile file) {
        try {
            boolean storyExists = storiesService.checkStoryExists(userId);
            if (storyExists) {
                storiesService.addMediaToExistingStory(userId, file);
                return ResponseEntity.ok(new ApiResponse(true, "New story added"));
            } else {
                storiesService.addNewStory(userId, file);
                return ResponseEntity.ok(new ApiResponse(true, "New story added"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error: " + e.getMessage()));
        }
    }
    @GetMapping
    public List<Story> getAllStory() {
        return storiesService.getAllStory();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Story> getStoryById(@PathVariable Long id) {
        return new ResponseEntity<>(storiesService.getStoryById(id), HttpStatus.OK);
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<?> getAllStoryHome(@PathVariable("userId") Long userId) {
        try {
            List<MediaStory> mediaStories = storiesService.getAllStoriesByUser(userId);
            return ResponseEntity.ok(new ApiResponseWithData<>(true, "Get All Stories By User", mediaStories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public Story updateStory(@PathVariable Long id, @RequestBody Story updatedStory) {
        Story existingStory = storiesService.getStoryById(id);
        updatedStory.setId(existingStory.getId());
        return storiesService.saveStory(updatedStory);
    }

    @DeleteMapping("/{id}")
    public void deleteStory(@PathVariable Long id) {
        storiesService.deleteStory(id);
    }
}
