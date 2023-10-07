package com.social.app.services;

import com.social.app.models.ListChat;
import com.social.app.repositories.ListChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ListChatService {

    private final ListChatRepository listChatsRepository;

    @Autowired
    public ListChatService(ListChatRepository listChatsRepository) {
        this.listChatsRepository = listChatsRepository;
    }

    public List<ListChat> getAllListChat() {
        return listChatsRepository.findAll();
    }

    public Optional<ListChat> getListChatById(Long uidListChat) {
        return listChatsRepository.findById(uidListChat);
    }

    public ListChat saveListChat(ListChat listChats) {
        return listChatsRepository.save(listChats);
    }

    public void deleteListChat(Long uidListChat) {
        listChatsRepository.deleteById(uidListChat);
    }

    // Add more business logic methods if needed
}
