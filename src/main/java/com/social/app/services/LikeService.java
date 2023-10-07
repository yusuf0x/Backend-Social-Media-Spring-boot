package com.social.app.services;

import com.social.app.exceptions.LikeNotFoundException;
import com.social.app.models.Like;
import com.social.app.models.Post;
import com.social.app.models.User;
import com.social.app.payload.request.AddLike;
import com.social.app.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class LikeService {

    private final LikeRepository likesRepository;

    @Autowired
    public LikeService(LikeRepository likesRepository) {
        this.likesRepository = likesRepository;
    }

    public List<Like> getAllLike() {
        return likesRepository.findAll();
    }

    public Like getLikeById(Long uidLike) {
        return likesRepository.findById(uidLike).orElseThrow(
                () -> new LikeNotFoundException("Like Not Found with id = "+uidLike)
        );
    }
    public Like saveLike(AddLike like) {
        Like like1 = new Like();
        return likesRepository.save(like1);
    }
    public Like save(Like like) {
        return likesRepository.save(like);
    }

    public void deleteLike(Long uidLike) {
        Like like = getLikeById(uidLike);
        likesRepository.delete(like);
    }
    public boolean existsByUserAndPost(User user, Post post){
        return likesRepository.existsByUserAndPost(user,post);
    }
    public void deleteByUserAndPost(User user,Post post){
        likesRepository.deleteByUserAndPost(user,post);
    }

}
