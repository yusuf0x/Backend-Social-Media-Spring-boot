package com.social.app.repositories;

import com.social.app.models.Person;
import com.social.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPerson(Person person);
    Boolean existsByUsername(String username);
    User findByUsername(String username);
    Boolean existsByEmail(String email);

}