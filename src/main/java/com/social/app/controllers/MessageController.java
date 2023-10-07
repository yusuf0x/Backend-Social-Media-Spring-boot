package com.social.app.controllers;

import com.social.app.models.Message;
import com.social.app.payload.response.ApiResponse;
import com.social.app.payload.response.ApiResponseWithData;
import com.social.app.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final MessageService messagesService;

    @Autowired
    public MessageController(MessageService messagesService) {
        this.messagesService = messagesService;
    }

    @PostMapping
    public Message createMessage(@RequestBody Message messages) {
        return messagesService.saveMessage(messages);
    }

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessage() {
        return ResponseEntity.ok(messagesService.getAllMessage());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        return new ResponseEntity<>(messagesService.getMessageById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable Long id, @RequestBody Message updatedMessage) {
        Message existingMessage = messagesService.getMessageById(id);
        updatedMessage.setId(existingMessage.getId());
        return new ResponseEntity<>(messagesService.saveMessage(updatedMessage),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable Long id) {
        messagesService.deleteMessage(id);
    }


    @GetMapping("/by-user/{userId}")
    public ResponseEntity<?> getListMessagesByUser(@PathVariable("userId") Long userId) {
        try {
            List<Message> listChat = messagesService.getAllMessagesByUser(userId);
            return ResponseEntity.ok(new ApiResponseWithData<>(true, "All Messages list by user", listChat));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error: " + e.getMessage()));
        }
    }
//    public void insertListChat(String uidSource, String uidTarget) {
//        try {
//            long chatCount = listChatRepository.countBySourceUidAndTargetUid(uidSource, uidTarget);
//
//            if (chatCount == 0) {
//                ListChat listChat = ListChat.builder()
//                        .uidListChat(UUID.randomUUID().toString())
//                        .sourceUid(uidSource)
//                        .targetUid(uidTarget)
//                        .build();
//                listChatRepository.save(listChat);
//            }
//        } catch (Exception e) {
//            // Handle exceptions
//        }
//    }
}
