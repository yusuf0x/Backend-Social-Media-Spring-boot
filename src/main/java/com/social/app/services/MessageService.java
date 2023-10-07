package com.social.app.services;

import com.social.app.exceptions.MessageNotFoundException;
import com.social.app.models.Message;
import com.social.app.models.User;
import com.social.app.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messagesRepository;
    private final UserService userService;

    @Autowired
    public MessageService(MessageRepository messagesRepository, UserService userService) {
        this.messagesRepository = messagesRepository;
        this.userService = userService;
    }

    public List<Message> getAllMessage() {
        return messagesRepository.findAll();
    }

    public Message getMessageById(Long uidMessage) {
        return messagesRepository.findById(uidMessage).orElseThrow(
                () -> new MessageNotFoundException(
                        "Message Not Found with id = "+uidMessage
                )
        );
    }
    public Message saveMessage(Message messages) {
        return messagesRepository.save(messages);
    }
    public void deleteMessage(Long uidMessage) {
        Message message = getMessageById(uidMessage);
        messagesRepository.delete(message);
    }

    public  List<Message> getAllMessagesByUser(Long userId){
        User user = userService.getUserById(userId);
        return messagesRepository.findByUser(user);
    }

    public void addNewMessage(Long sourceId, Long targetId, String message) {
        User source = userService.getUserById(sourceId);
        User target = userService.getUserById(targetId);
        Message newMessage = Message.builder()
                    .source(source)
                    .target(target)
                    .message(message)
                    .build();
        messagesRepository.save(newMessage);
    }
}

