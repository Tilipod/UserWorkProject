package ru.tilipod.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Модель статуса пользователя. Содержит информацию о статусе пользователя и метку времени его последней смены.
 * Статус пользователя выделен в отдельную модель для сокращения нагрузки на БД.
 * @author Tilipod
 */
@Entity
@Table(name = "user_status")
// Считаем, что id статуса и модель пользователя в нем клиенту без надобности
@JsonIgnoreProperties(value = {"id", "user", "hibernateLazyInitializer"})
public class UserStatus {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private boolean isOnline;

    @Column(name = "change_timestamp")
    private Timestamp changeTimestamp;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public UserStatus() { }

    public UserStatus(UserStatus userStatus) {
        this.id = userStatus.getId();
        this.isOnline = userStatus.getIsOnline();
        this.changeTimestamp = userStatus.getChangeTimestamp();
        this.user = userStatus.getUser();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public Timestamp getChangeTimestamp() {
        return changeTimestamp;
    }

    public void setChangeTimestamp(Timestamp changeTimestamp) {
        this.changeTimestamp = changeTimestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

