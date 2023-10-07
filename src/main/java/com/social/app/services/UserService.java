package com.social.app.services;

import com.social.app.exceptions.UserNotFoundException;
import com.social.app.models.*;
import com.social.app.payload.request.AcceptFollowerRequest;
import com.social.app.payload.request.CreateUserRequest;
import com.social.app.payload.request.UpdateProfileInfoRequest;
import com.social.app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final PersonService personService;
    private final FriendService friendService;
    private final FollowerService followerService;

    private final  NotificationService notificationService;


    @Autowired
    public UserService(UserRepository usersRepository, PasswordEncoder passwordEncoder, PersonService personService,  FriendService friendService, FollowerService followerService, NotificationService notificationService) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.personService = personService;
        this.friendService = friendService;
        this.followerService = followerService;
        this.notificationService = notificationService;
    }

    public List<User> getAllUser() {
        return usersRepository.findAll();
    }

    public User getUserById(Long uid) {
        return usersRepository.findById(uid).orElseThrow(
                () -> new UserNotFoundException("User Not Found with id = "+uid)
        );
    }
    public User getUserByUserId(Long uid) {
        return getUserById(uid);
    }

    public User save(User user) {
        return usersRepository.save(user);
    }
    public User saveUser(CreateUserRequest request) {
        int randomNumber = (int) (10000 + Math.random() * 90000);
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .tokenTemp(String.valueOf(randomNumber))
                .build();
        Person person = Person.builder()
                .fullname(request.getFirstname()+" "+request.getLastname())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        personService.savePerson(person);
        user.setPerson(person);
        return usersRepository.save(user);
    }
    public User updateUser(User user){
        return usersRepository.save(user);
    }
    public void deleteUser(Long uid) {
        User user = getUserById(uid);
        usersRepository.delete(user);
    }
    public User findByEmail(String email){
        return usersRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User Not Found with email = "+email)
        );
    }
    public void updateUserProfile(Long id, UpdateProfileInfoRequest request){
        User user = getUserByUserId(id);
        user.setUsername(request.getUsername());
        user.setDescription(request.getDescription());
        user.getPerson().setFullname(request.getFullname());
        user.getPerson().setPhone(request.getPhone());
        this.save(user);
    }
    public boolean isPasswordCorrect(Long id, String currentPassword) {
        User user = getUserByUserId(id);
        String storedPasswordHash = user.getPassword();
        return BCrypt.checkpw(currentPassword, storedPasswordHash);
    }
    public void updateUserPassword(Long id,String newPassword){
        User user = getUserByUserId(id);
        user.setPassword(newPassword);
        usersRepository.save(user);
    }
    public User searchUserByUsername(String username){
        return usersRepository.findByUsername(username);
    }
    public boolean isAccountPrivate(Long userId){
        User user = getUserByUserId(userId);
        return user.isPrivate();
    }

    public void addFriend(Long userId, Long friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        if (user != null && friend != null) {
            Friend friendship = new Friend();
            friendship.setDateFriend(new Date());
            friendship.setFriend(friend);
            friendship.setUser(user);
            friendService.saveFriend(friendship);
        }
    }

    public void addFollower(Long userId, Long followerId) {
        User user = getUserById(userId);
        User follower = getUserById(followerId);
        Follower followerRelationship = new Follower();
        followerRelationship.setDateFollower(new Date());
        followerRelationship.setUser(user);
        followerRelationship.setFollower(follower);
        followerService.saveFollower(followerRelationship);
    }
    public void sendFollowRequest(Long userId, Long followerId) {
        User user = getUserById(userId);
        User follower = getUserById(followerId);
            Notification existingNotification = notificationService.findByUserAndFollower(user, follower);
            if (existingNotification == null) {
                Notification notification = new Notification();
                notification.setTypeNotification("1");
                notificationService.saveNotification(notification);
            }
    }

    public void acceptFollowerRequest(Long userId,AcceptFollowerRequest acceptRequest) {
        User friend = getUserById(acceptRequest.getFriendId());
        Notification notification = notificationService.getNotificationById(acceptRequest.getNotificationId());
        User user = getUserById(userId);
        Follower followerRelationship = new Follower();
        followerRelationship.setDateFollower(new Date());
        followerRelationship.setUser(user);
        followerRelationship.setFollower(friend);
        followerService.saveFollower(followerRelationship);
        Friend friendship = new Friend();
        friendship.setDateFriend(new Date());
        friendship.setFriend(friend);
        friendship.setUser(user);
        friendService.saveFriend(friendship);
        notification.setTypeNotification("3");
        notificationService.saveNotification((notification));
    }
    public void deleteFollowing(Long userId, Long friendId) {
        User user = getUserById(userId);
        User friend = getUserById(userId);
    }
    public List<User> getAllFollowings(Long userId) {
        User user = getUserById(userId);
        return null;
    }
    public List<User> getAllFollowers(Long userId) {
        User user = getUserById(userId);
        return null;
    }
    public void updateOnlineUser(Long uid) {
        User user = getUserById(uid);
        user.setOnline(true);
        usersRepository.save(user);
    }

    public void updateOfflineUser(Long uid) {
        User user = getUserById(uid);
        user.setOnline(false);
        usersRepository.save(user);
    }
}
