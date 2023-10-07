package com.social.app.services;

import com.social.app.exceptions.FriendNotFoundException;
import com.social.app.models.Friend;
import com.social.app.repositories.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FriendService {

    private final FriendRepository friendsRepository;

    @Autowired
    public FriendService(FriendRepository friendsRepository) {
        this.friendsRepository = friendsRepository;
    }
    public List<Friend> getAllFriend() {
        return friendsRepository.findAll();
    }
    public Friend getFriendById(Long uid) {
        return friendsRepository.findById(uid).orElseThrow(
                () -> new FriendNotFoundException("Friend Not Found with id = "+uid)
        );
    }
    public Friend saveFriend(Friend friends) {
        return friendsRepository.save(friends);
    }
    public void deleteFriend(Long uid) {
        Friend friend = getFriendById(uid);
        friendsRepository.delete(friend);
    }
}
