package ru.tilipod.services.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tilipod.models.User;
import ru.tilipod.models.UserStatus;
import ru.tilipod.repositories.UserRepository;
import ru.tilipod.repositories.UserStatusRepository;
import ru.tilipod.services.users.exceptions.UserNotFoundException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserWorkService implements UserService {

    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;

    @Autowired
    public UserWorkService(UserRepository userRepository, UserStatusRepository userStatusRepository) {
        this.userRepository = userRepository;
        this.userStatusRepository = userStatusRepository;
    }

    /**
     * @see UserService#createUser
     * @author Tilipod
     */
    @Override
    public void createUser(User user) {
        userRepository.save(user);

        // При создании пользователя создаем и статус для него
        UserStatus userStatus = new UserStatus();
        userStatus.setUser(user);
        userStatus.setIsOnline(true);
        userStatus.setChangeTimestamp(new Timestamp(System.currentTimeMillis()));
        userStatusRepository.save(userStatus);
    }

    /**
     * @see UserService#getById
     * @author Tilipod
     */
    @Override
    public User getById(long id) throws UserNotFoundException {
        User user = userRepository.getById(id);
        if (user == null)
            throw new UserNotFoundException("User not found");
        return user;
    }

    /**
     * @see UserService#changeUserStatus
     * @author Tilipod
     */
    @Override
    public UserStatus changeUserStatus(long id, UserStatus newUserStatus) throws UserNotFoundException {
        UserStatus userStatus = userStatusRepository.findByUserId(id);
        if (userStatus == null)
            throw new UserNotFoundException("User and user_status not found");

        UserStatus oldUserStatus = new UserStatus(userStatus);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        userStatus.setIsOnline(newUserStatus.getIsOnline());
        userStatus.setChangeTimestamp(timestamp);

        newUserStatus.setChangeTimestamp(timestamp);

        userStatusRepository.save(userStatus);

        return oldUserStatus;
    }

    /**
     * @see UserService#getAllIf
     * @author Tilipod
     */
    @Override
    public List<User> getAllIf(Boolean isOnline, Timestamp timestamp) {
        List<User> users = new ArrayList<>();
        List<UserStatus> statuses = new ArrayList<>();
        if (isOnline == null && timestamp == null)
            statuses = userStatusRepository.findAll();
        else if (isOnline != null && timestamp != null)
            statuses = userStatusRepository.findByIsOnlineAndChangeTimestampAfter(isOnline, timestamp);
        else if (isOnline != null)
            statuses = userStatusRepository.findByIsOnline(isOnline);
        else
            statuses = userStatusRepository.findByChangeTimestampAfter(timestamp);

        for (UserStatus userStatus : statuses)
            users.add(userStatus.getUser());
        return users;
    }
}
