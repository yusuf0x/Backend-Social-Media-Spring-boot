package com.social.app.services;

import com.social.app.exceptions.PostNotFoundException;
import com.social.app.models.Like;
import com.social.app.models.Post;
import com.social.app.models.PostSave;
import com.social.app.models.User;
import com.social.app.payload.request.NewPostRequest;
import com.social.app.payload.request.SavePostRequest;
import com.social.app.repositories.PostRepository;
import com.social.app.repositories.PostSaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postsRepository;
    private final UserService userService;
    private final PostSaveRepository postSaveRepository;
    private final LikeService likeService;
    private final FileService fileService;

    @Autowired
    public PostService(PostRepository postsRepository, UserService userService, PostSaveRepository postSaveRepository, LikeService likeService, FileService fileService) {
        this.postsRepository = postsRepository;
        this.userService = userService;
        this.postSaveRepository = postSaveRepository;
        this.likeService = likeService;
        this.fileService = fileService;
    }

    public List<Post> getAllPost() {
        return postsRepository.findAll();
    }

    public Post getPostById(Long postId) {
        return postsRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException(
                        "Post Not Found with id = "+postId
                )
        );
    }

    public Post savePost(Post post) {
        return postsRepository.save(post);
    }

    public void deletePost(Long postId) {
        Post post = getPostById(postId);
        postsRepository.delete(post);
    }

    public  Post createPost(NewPostRequest request,MultipartFile file) throws IOException {
        User user = userService.getUserById(request.getUserId());
        Post post = new Post();
        String filename = fileService.uploadImage("/posts/",file);
        post.setImage(filename);
        post.setUpdatedAt(new Date());
        post.setContent(request.getContent());
        post.setCreatedAt(new Date());
        post.setTypePrivacy(request.getTypePrivacy());
        post.setUser(user);
        post.setTags(request.getTags());
        return postsRepository.save(post);
    }
    public void savePostByUser(SavePostRequest savePostRequest){
        User user = userService.getUserById(savePostRequest.getUserId());
        Post post = getPostById(savePostRequest.getPostId());
        PostSave postSave = PostSave.builder()
                .dateSave(new Date())
                .post(post)
                .user(user)
                .build();
        postSaveRepository.save(postSave);
    }
    public  List<Post> getPostsByUserId(Long id){
        User user = userService.getUserById(id);
        return postsRepository.findByUser(user);
    }
    public  List<PostSave> getListSavedPostsByUser(Long id){
        User user = userService.getUserById(id);
        return postSaveRepository.findByUser(user);
    }
    public boolean likeOrUnlikePost(Long postId, Long userId) {
        User user =userService.getUserById(userId);
        Post post = getPostById(postId);
        boolean isLiked = likeService.existsByUserAndPost(user, post);
        if (isLiked) {
            likeService.deleteByUserAndPost(user, post);
        } else {
            Like like =Like.builder()
                    .createdAt(new Date())
                    .post(post)
                    .user(user)
                    .build();
            likeService.save(like);
//             Additional logic to create notifications, if needed
        }
        return !isLiked;
    }

}

