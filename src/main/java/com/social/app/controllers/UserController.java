package com.social.app.controllers;

import com.social.app.models.User;
import com.social.app.payload.request.*;
import com.social.app.payload.response.ApiResponse;
import com.social.app.payload.response.ApiResponseWithData;
import com.social.app.payload.response.MessageResponse;
import com.social.app.services.FileService;
import com.social.app.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService usersService;
    private final FileService fileService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService usersService, FileService fileService, PasswordEncoder passwordEncoder) {
        this.usersService = usersService;
        this.fileService = fileService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public User createUser(@RequestBody CreateUserRequest user) {
        return usersService.saveUser(user);
    }

    @GetMapping
    public List<User> getAllUser() {
        return usersService.getAllUser();
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(usersService.getUserById(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User existingUser = usersService.getUserById(id);
        updatedUser.setId(existingUser.getId());
        return usersService.updateUser(updatedUser);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        usersService.deleteUser(id);
    }

    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam String email, @RequestParam String code) {
        try {
            User  user = usersService.findByEmail(email);
            String tokenTemp = user.getTokenTemp();
            if (!code.equals(tokenTemp)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse( "Unsuccessful verification. The verification code is wrong."));
            }
            user.setEmailVerified(true);
            user.setTokenTemp("");
            usersService.save(user);
            return ResponseEntity.ok(new MessageResponse("Welcome successfully validated"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/{userId}/update-cover")
    public ResponseEntity<ApiResponse> updatePictureCover(
            @RequestPart("file") MultipartFile file,
            @PathVariable Long userId
    ) throws IOException {
        User user =  usersService.getUserByUserId(userId);
        String fileName = fileService.saveCoverImage(user,file);
        user.getPerson().setCover(fileName);
        usersService.save(user);
        return ResponseEntity.ok(new ApiResponse(true, "Cover updated successfully"));
    }
    @PostMapping("/{userId}/update-profile-image")
    public ResponseEntity<ApiResponse> updateProfile(
            @RequestPart("file") MultipartFile file,
            @PathVariable Long userId
    ) throws IOException {
        User user = usersService.getUserByUserId(userId);
        String fileName = fileService.saveProfileImage(user,file);
        user.getPerson().setImage(fileName);
        usersService.save(user);
        return ResponseEntity.ok(new ApiResponse(true, "Profile Image updated successfully"));
    }
    @PostMapping("/{userId}/update-profile-info")
    public ResponseEntity<?> updateProfile(@PathVariable Long userId, @RequestBody UpdateProfileInfoRequest updateProfileRequest) {
        try {
            User user = usersService.getUserByUserId(userId);
            usersService.updateUserProfile(userId, updateProfileRequest);
            return ResponseEntity.ok(new ApiResponse(true, "Updated profile"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PostMapping("/{userId}/change-password/")
    public ResponseEntity<?> changePassword(@PathVariable Long userId, @RequestBody ChangePasswordRequest changePasswordRequest) {
        try {
            usersService.getUserByUserId(userId);
            if (!usersService.isPasswordCorrect(userId, changePasswordRequest.getCurrentPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "Password does not match"));
            }
            usersService.updateUserPassword(userId, passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            return ResponseEntity.ok(new ApiResponse(true, "Password changed successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, e.getMessage()));
        }
    }
    @PostMapping("/{userId}/change-account-privacy")
    public ResponseEntity<?> changeAccountPrivacy(@PathVariable Long userId) {
        try {
            User user = usersService.getUserByUserId(userId);
            boolean newPrivacy = !user.isPrivate();
            user.setPrivate(newPrivacy);
            usersService.save(user);
            return ResponseEntity.ok(new ApiResponse(true, "Account changed successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, e.getMessage()));
        }
    }
    @GetMapping("/search-user")
    public ResponseEntity<?> searchUserByUsername(@RequestParam String username) {
        try {
            User user = usersService.searchUserByUsername(username);
            if (user != null) {
                return ResponseEntity.ok(new ApiResponseWithData<>(true,"Username exists",user));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "User not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, e.getMessage()));
        }
    }
    @GetMapping("/{userId}/another-user/")
    public ResponseEntity<?> getAnotherUserById(@PathVariable Long userId, @RequestParam String anotherUserId) {
        try {
//            User user = usersService.getUserByUserId(idUser, idUser);
//            if (anotherUserResponse != null) {
//                return ResponseEntity.ok(new ApiResponseWithData<>(true,"user found",));
//            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "User not found"));
//            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, e.getMessage()));
        }
    }
    @PostMapping("/{userId}/add-new-following/")
    public ResponseEntity<?> addNewFollowing(@PathVariable Long userId, @RequestBody AddNewFollowingRequest addNewFollowingRequest) {
        try {
            boolean isPrivateAccount = usersService.isAccountPrivate(addNewFollowingRequest.getUserId());
            if (!isPrivateAccount) {
                usersService.addFriend(userId, addNewFollowingRequest.getUserId());
                usersService.addFollower(addNewFollowingRequest.getUserId(), userId);
                return ResponseEntity.ok(new ApiResponse(true, "New friend"));
            } else {
                usersService.sendFollowRequest(addNewFollowingRequest.getUserId(), userId);
                return ResponseEntity.ok(new ApiResponse(true, "Follow request sent"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, e.getMessage()));
        }
    }
    @PostMapping("/{userId}/accept-follower-request")
    public ResponseEntity<?> acceptFollowerRequest(@PathVariable Long userId,@RequestBody AcceptFollowerRequest acceptRequest) {
        try {
            usersService.acceptFollowerRequest(userId,acceptRequest);
            return ResponseEntity.ok(new ApiResponse(true, "Follower request accepted"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, e.getMessage()));
        }
    }
    @DeleteMapping("/{userId}/delete-following")
    public ResponseEntity<?> deleteFollowing(@PathVariable Long userId, @RequestParam Long friendId) {
        try {
            usersService.deleteFollowing(userId, friendId);
            return ResponseEntity.ok(new ApiResponse(true, "Friend deleted"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, e.getMessage()));
        }
    }
    @GetMapping("/{userId}/all-followings")
    public ResponseEntity<?> getAllFollowings(@PathVariable Long userId) {
        try {
//            List<User> followings = usersService.getAllFollowings(userId);
//            return ResponseEntity.ok(new ApiResponseWithData<List<User>>(true, "Get all followings", followings));
            return null;

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, e.getMessage()));
        }
    }
    @GetMapping("/{userId}/all-followers")
    public ResponseEntity<?> getAllFollowers(@PathVariable Long userId) {
        try {
            List<User> followers = usersService.getAllFollowers(userId);
//            return ResponseEntity.ok(new ApiResponseWithData<List<User>>(true, "Get all followers", followers));
                return null;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PostMapping("/{userId}/update-online-user/")
    public ResponseEntity<?> updateOnlineUser(@PathVariable Long userId) {
        try {
            usersService.updateOnlineUser(userId);
            return ResponseEntity.ok(new ApiResponse(true, "User updated online status"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, e.getMessage()));
        }
    }
    @PostMapping("/{userId}/update-offline-user/")
    public ResponseEntity<?> updateOfflineUser(@PathVariable Long userId) {
        try {
            usersService.updateOfflineUser(userId);
            return ResponseEntity.ok(new ApiResponse(true, "User updated online status"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, e.getMessage()));
        }
    }
}
