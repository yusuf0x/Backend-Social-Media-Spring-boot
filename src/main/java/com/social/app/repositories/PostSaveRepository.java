package com.social.app.repositories;

import com.social.app.models.PostSave;
import com.social.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostSaveRepository extends JpaRepository<PostSave, Long> {
    List<PostSave> findByUser(User user);
}
