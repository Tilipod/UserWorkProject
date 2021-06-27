package ru.tilipod.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Email;

/**
 * Модель пользователя. Содержит информацию о пользователе
 * @author Tilipod
 */
@Entity
@Table(name = "users")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(name = "portrait")
    private String url;

    @NotNull
    private String name;

    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @OneToOne(mappedBy="user")
    private UserStatus userStatus;

    public User() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
}
