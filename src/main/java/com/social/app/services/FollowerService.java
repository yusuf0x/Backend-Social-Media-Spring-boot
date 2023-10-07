package com.social.app.services;

import com.social.app.exceptions.FollowerNotFoundException;
import com.social.app.models.Follower;
import com.social.app.repositories.FollowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FollowerService {

    private final FollowerRepository followersRepository;

    @Autowired
    public FollowerService(FollowerRepository followersRepository) {
        this.followersRepository = followersRepository;
    }

    public List<Follower> getAllFollower() {
        return followersRepository.findAll();
    }
    public Follower getFollowerById(Long uid) {
        return followersRepository.findById(uid).orElseThrow(
                () -> new FollowerNotFoundException("Follower Not Found with id = "+uid)
        );
    }
    public Follower saveFollower(Follower followers) {
        return followersRepository.save(followers);
    }

    public void deleteFollower(Long uid) {
        Follower follower = getFollowerById(uid);
        followersRepository.delete(follower);
    }
}
