package com.social.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String description;
    private boolean isPrivate = false;
    private boolean isOnline = false;
    private String email;

    @JsonIgnore
    private String password;
    private boolean emailVerified = false;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
    private String notificationToken;
    private String tokenTemp;
}