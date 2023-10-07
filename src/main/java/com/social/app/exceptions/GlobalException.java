package com.social.app.exceptions;

import com.social.app.payload.ErrorObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;


@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorObject> handleUserNotFoundException(
            UserNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ErrorObject> handlePersonNotFoundException(
            PersonNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorObject> handlePostNotFoundException(
            PostNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorObject> handleCommentNotFoundException(
            CommentNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(LikeNotFoundException.class)
    public ResponseEntity<ErrorObject> handleLikeNotFoundException(
            LikeNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(FollowerNotFoundException.class)
    public ResponseEntity<ErrorObject> handleFollowerNotFoundException(
            FollowerNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(FriendNotFoundException.class)
    public ResponseEntity<ErrorObject> handleFriendNotFoundException(
            FriendNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<ErrorObject> handleNotificationNotFoundException(
            NotificationNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<ErrorObject> handleMessageNotFoundException(
            MessageNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
//    @ExceptionHandler(FavoriteNotFoundException.class)
//    public ResponseEntity<ErrorObject> handleFavoriteNotFoundException(
//            FavoriteNotFoundException ex, WebRequest request
//    ){
//        ErrorObject errorObject = new ErrorObject();
//
//        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
//        errorObject.setMessage(ex.getMessage());
//        errorObject.setTimestamp( LocalDateTime.now());
//        return new ResponseEntity<>(
//                errorObject, HttpStatus.NOT_FOUND
//        );
//    }

    @ExceptionHandler(JwtExpirationException.class)
    public ResponseEntity<Object> handleJwtExpirationException(JwtExpirationException ex, WebRequest request) {
//        // Create a custom error response
//        Map<String, Object> responseBody = new HashMap<>();
//        responseBody.put("timestamp", new Date());
//        responseBody.put("status", HttpStatus.UNAUTHORIZED.value());
//        responseBody.put("error", "Unauthorized");
//        responseBody.put("message", ex.getMessage());
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        errorObject.setMessage("JWT token is expired");
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
}
