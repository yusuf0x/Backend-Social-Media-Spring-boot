package com.social.app.controllers;

import com.social.app.models.Post;
import com.social.app.services.FileService;
import com.social.app.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/v1/uploads")
public class UploadController {
    private final FileService fileService;
    private final PostService postService;

    public UploadController(FileService fileService, PostService postService) {
        this.fileService = fileService;
        this.postService = postService;
    }
    @GetMapping(value ="/post-image/{postId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("postId") Long postId,
            HttpServletResponse response) throws IOException {
        Post post = postService.getPostById(postId);
        InputStream resource = fileService.getResource("/posts", post.getImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
