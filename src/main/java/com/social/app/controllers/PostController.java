package com.social.app.controllers;
import com.social.app.models.Post;
import com.social.app.models.PostSave;
import com.social.app.payload.request.NewPostRequest;
import com.social.app.payload.request.SavePostRequest;
import com.social.app.payload.response.ApiResponse;
import com.social.app.payload.response.ApiResponseWithData;
import com.social.app.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
@RestController
@RequestMapping("/api/v1/posts/")
public class PostController {
    private final PostService postsService;
    @Autowired
    public PostController(PostService postsService) {
        this.postsService = postsService;
    }
    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postsService.savePost(post);
    }
    @GetMapping
    public ResponseEntity<?> getAllPost() {
        try {
            List<Post> posts = postsService.getAllPost();
            return ResponseEntity.ok(new ApiResponseWithData<>(true, "Get All Post", posts));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse(false, e.getMessage()));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return new ResponseEntity<>(postsService.getPostById(id), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post updatedPost) {
        Post existingPost = postsService.getPostById(id);
        updatedPost.setId(existingPost.getId());
        return postsService.savePost(updatedPost);
    }
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postsService.deletePost(id);
    }
    @PostMapping(value = "/create-post",consumes = {"multipart/form-data"})
    public ResponseEntity<?> createNewPost(@RequestPart(name = "post") NewPostRequest newPostRequest,
                                           @RequestParam("file") MultipartFile file) {
        try {
            postsService.createPost(newPostRequest, file);
            return ResponseEntity.ok(new ApiResponse(true, "Posted"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse(false, e.getMessage()));
        }
    }
    @GetMapping("/get-by-user/{userId}")
    public ResponseEntity<?> getPostsByUserId(@PathVariable("userId") Long userId) {
        try {
            List<Post> posts = postsService.getPostsByUserId(userId);
            return ResponseEntity.ok(new ApiResponseWithData<>(true, "Get Posts by user ID", posts));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse(false, e.getMessage()));
        }
    }
    @PostMapping("/save-post")
    public ResponseEntity<?> savePostByUser(@RequestBody SavePostRequest savePostRequest) {
        try {
            postsService.savePostByUser(savePostRequest);
            return ResponseEntity.ok(new ApiResponse(true, "Posted save"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse(false, e.getMessage()));
        }
    }
    @GetMapping("/list-saved-by-user/{userId}")
    public ResponseEntity<?> getListSavedPostsByUser(@PathVariable("userId") Long userId) {
        try {
            List<PostSave> listSavedPost = postsService.getListSavedPostsByUser(userId);
            return ResponseEntity.ok(new ApiResponseWithData<>(true, "List Saved Post", listSavedPost));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse(false, e.getMessage()));
        }
    }
    @PostMapping("/like-or-unlike")
    public ResponseEntity<?> likeOrUnlikePost(@RequestParam("userId") Long userId,@RequestParam("postId") Long postId) {
        try {
            boolean isLiked = postsService.likeOrUnlikePost(userId,postId);
            String message = isLiked ? "like" : "unlike";
            return ResponseEntity.ok(new ApiResponse(true, message));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse(false, e.getMessage()));
        }
    }
}
