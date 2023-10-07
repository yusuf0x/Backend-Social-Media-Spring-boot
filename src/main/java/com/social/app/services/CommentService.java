package com.social.app.services;

import com.social.app.exceptions.CommentNotFoundException;
import com.social.app.models.Comment;
import com.social.app.models.Post;
import com.social.app.models.User;
import com.social.app.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentsRepository;
    private  final  PostService postService;
    private final UserService userService;

    public CommentService(CommentRepository commentsRepository, PostService postService, UserService userService) {
        this.commentsRepository = commentsRepository;
        this.postService = postService;
        this.userService = userService;
    }


    public List<Comment> getAllComment() {
        return commentsRepository.findAll();
    }

    public Comment getCommentById(Long uid) {
        return commentsRepository.findById(uid).orElseThrow(
                () -> new CommentNotFoundException("Comment Not Found with id = "+uid)
        );
    }

    public Comment saveComment(Comment comment) {
        return commentsRepository.save(comment);
    }

    public void deleteComment(Long uid) {
        Comment comment = getCommentById(uid);
        commentsRepository.delete(comment);
    }

    public  Comment addNewComment(Long userId,Long postId,String data){
        User user = userService.getUserById(userId);
        Post post = postService.getPostById(postId);
        Comment comment = Comment.builder()
                .comment(data)
                .user(user)
                .post(post)
                .createdAt(new Date())
                .isLike(false)
                .build();
        return this.saveComment(comment);
    }
    public List<Comment> getCommentsByPostId(Long postId){
        Post post = postService.getPostById(postId);
        return commentsRepository.findByPost(post);
    }
    public List<Comment> getCommentsByUserId(Long userId){
        User user = userService.getUserById(userId);
        return commentsRepository.findByUser(user);
    }
}
