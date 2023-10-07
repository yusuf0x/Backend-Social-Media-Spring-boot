package com.social.app.repositories;

import com.social.app.models.ListChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListChatRepository extends JpaRepository<ListChat, Long> {
}
