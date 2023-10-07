package com.social.app.services;

import com.social.app.models.PostSave;
import com.social.app.repositories.PostSaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PostSaveService {

    private final PostSaveRepository postSaveRepository;

    @Autowired
    public PostSaveService(PostSaveRepository postSaveRepository) {
        this.postSaveRepository = postSaveRepository;
    }

    public List<PostSave> getAllPostSaves() {
        return postSaveRepository.findAll();
    }

    public Optional<PostSave> getPostSaveById(Long postSaveUid) {
        return postSaveRepository.findById(postSaveUid);
    }

    public PostSave savePostSave(PostSave postSave) {
        return postSaveRepository.save(postSave);
    }

    public void deletePostSave(Long postSaveUid) {
        postSaveRepository.deleteById(postSaveUid);
    }

}
