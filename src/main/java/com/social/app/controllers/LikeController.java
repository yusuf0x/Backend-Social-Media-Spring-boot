package com.social.app.controllers;

import com.social.app.models.Comment;
import com.social.app.models.Like;
import com.social.app.payload.request.AddLike;
import com.social.app.payload.response.ApiResponse;
import com.social.app.payload.response.ApiResponseWithData;
import com.social.app.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {

    private final LikeService likesService;

    @Autowired
    public LikeController(LikeService likesService) {
        this.likesService = likesService;
    }

    @PostMapping
    public ResponseEntity<?> createLike(@RequestBody AddLike like) {
        try {
            likesService.saveLike(like);
            return ResponseEntity.ok(new ApiResponse(true, "New Like Added"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse(false, e.getMessage()));
        }
    }
    @GetMapping
    public ResponseEntity<List<Like>> getAllLike() {
        return new ResponseEntity(new ApiResponseWithData<>(true,"All Likes",likesService.getAllLike()),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLikeById(@PathVariable Long id) {
        return new ResponseEntity<>(new ApiResponseWithData<>(true,"Get Like by id ",likesService.getLikeById(id)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLike(@PathVariable Long id, @RequestBody Like updatedLike) {
        Like existingLike = likesService.getLikeById(id);
        updatedLike.setId(existingLike.getId());
        return ResponseEntity.ok(new ApiResponseWithData<>(true,"Update",likesService.saveLike(null)));
    }
    @DeleteMapping("/{id}")
    public void deleteLike(@PathVariable Long id) {
        likesService.deleteLike(id);
    }
}
